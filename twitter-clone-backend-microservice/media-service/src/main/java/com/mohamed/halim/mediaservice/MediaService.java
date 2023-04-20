package com.mohamed.halim.mediaservice;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TIFF;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp4.MP4Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.google.common.io.Files;
import com.mohamed.halim.dtos.MediaDto;
import com.mohamed.halim.mediaservice.model.Media;
import com.mohamed.halim.mediaservice.model.MediaDimension;
import com.mohamed.halim.mediaservice.model.MediaType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MediaService {
    private MediaRepository mediaRepository;

    public MediaDto saveMedia(MultipartFile mediaFile)
            throws IllegalStateException, IOException, SAXException, TikaException {
        File file = saveMediaFile(mediaFile);
        MediaType type = getFileType(file);

        Media media = Media.builder().url(convertToUrl(file))
                .type(type)
                .dimentions(getFileDimentions(file, type))
                .duration(getDuration(file, type))
                .build();

        return convertToDto(mediaRepository.save(media));
    }

    private Long getDuration(File file, MediaType type) throws IOException, SAXException, TikaException {
        if (type != MediaType.VIDEO)
            return null;
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext pcontext = new ParseContext();

        MP4Parser MP4Parser = new MP4Parser();
        MP4Parser.parse(inputstream, handler, metadata, pcontext);
        return (long) Double.parseDouble(metadata.get(XMPDM.DURATION));

    }

    private MediaDimension getFileDimentions(File file, MediaType type)
            throws IOException, SAXException, TikaException {
        if (type == MediaType.IMAGE) {
            return getImageDimentions(file);
        } else {
            return getVideoDimentions(file);
        }
    }

    private MediaDimension getImageDimentions(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        return new MediaDimension(height, width);
    }

    private MediaDimension getVideoDimentions(File file) throws IOException, SAXException, TikaException {

        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext pcontext = new ParseContext();

        MP4Parser MP4Parser = new MP4Parser();
        MP4Parser.parse(inputstream, handler, metadata, pcontext);

        return new MediaDimension(Integer.parseInt(metadata.get(TIFF.IMAGE_LENGTH)),
                Integer.parseInt(metadata.get(TIFF.IMAGE_WIDTH)));
    }

    public String convertToUrl(File file) {
        return "http://127.0.0.1:8080/api/v1/media/" + file.getName();
    }


    public File saveMediaFile(MultipartFile media) throws IllegalStateException, IOException {
        String name = String.format("%s.%s",
                UUID.randomUUID().toString(),
                Files.getFileExtension(media.getOriginalFilename()));
        File file = new File("media" + File.separator + name);
        Files.createParentDirs(file);
        media.transferTo(file.getAbsoluteFile());
        return file;
    }

    public Resource loadImage(String name) throws FileNotFoundException {
        File file = new File("media", name);
        return new InputStreamResource(new FileInputStream(file));
    }

    private MediaType getFileType(File file) {
        return switch (Files.getFileExtension(file.getName())) {
            case "webp", "jpeg", "jpg", "png" -> MediaType.IMAGE;
            default -> MediaType.VIDEO;
        };

    }
    @RabbitListener(queues = "media.get")
    public MediaDto getMedia(Long attacmentId) {
        return mediaRepository.findById(attacmentId).map(this::convertToDto).get();
    }
    public MediaDto convertToDto(Media media) {
        return new MediaDto(
                media.getId(),
                media.getType().toString(),
                media.getUrl(),
                media.getDuration(),
                media.getDimentions().getHeight(),
                media.getDimentions().getWidth()
        );
    }
}

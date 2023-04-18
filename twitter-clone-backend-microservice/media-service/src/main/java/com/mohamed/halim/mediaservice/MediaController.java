package com.mohamed.halim.mediaservice;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.mohamed.halim.dtos.MediaDto;

import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@RestController
@RequestMapping("/media")
@AllArgsConstructor
public class MediaController {
    private MediaService mediaService;

    @GetMapping(value = "/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource getMethodName(@PathVariable String name) throws FileNotFoundException {
        return mediaService.loadImage(name);
    }

    @PostMapping
    public MediaDto saveMedia(@RequestPart("media") MultipartFile mediaFile) throws IllegalStateException, IOException, SAXException, TikaException {
        return mediaService.saveMedia(mediaFile);
    }

}

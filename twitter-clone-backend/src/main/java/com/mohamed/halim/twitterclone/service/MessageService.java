package com.mohamed.halim.twitterclone.service;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.mohamed.halim.twitterclone.model.Message;
import com.mohamed.halim.twitterclone.model.dto.MediaDto;
import com.mohamed.halim.twitterclone.model.dto.MessageDto;
import com.mohamed.halim.twitterclone.repository.MessageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService {
    private final MediaService mediaService;
    private final MessageRepository messageRepository;
    private SimpMessagingTemplate messagingTemplate;

    public MediaDto postMedia(MultipartFile media)
            throws IllegalStateException, IOException, SAXException, TikaException {
        return mediaService.saveMedia(media);
    }

    public void handelMessage(MessageDto dto) {
        Message message = messageRepository.save(dto.toMessage());
        messagingTemplate.convertAndSend("/message/" + message.getTo(), message);
    }

    public List<MessageDto> getUserConversation(String from, String to) {
        return messageRepository.findAllByFromAndToOrderByTimeDesc(from, to, PageRequest.of(0, 20)).stream().map(message -> {
            MessageDto dto = MessageDto.fromMessage(message);
            dto.setMedia(mediaService.getMedia(message.getMediaId()));
            return dto;
        }).toList();
    }

}

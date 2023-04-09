package com.mohamed.halim.twitterclone.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.mohamed.halim.twitterclone.model.dto.MediaDto;
import com.mohamed.halim.twitterclone.model.dto.MessageDto;
import com.mohamed.halim.twitterclone.service.MessageService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @MessageMapping("/message")
    public void handelMessage(MessageDto message) {
        messageService.handelMessage(message);
    }

    @PostMapping("/media")
    public MediaDto postMedia(MultipartFile media)
            throws IllegalStateException, IOException, SAXException, TikaException {
        return messageService.postMedia(media);
    }

    @PostMapping("/{to}")
    public List<MessageDto> getUserConversation(@PathVariable String to, Principal principal) {
        return messageService.getUserConversation(principal.getName(), to);
    }

}

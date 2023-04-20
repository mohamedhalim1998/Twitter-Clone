package com.mohamed.halim.messagesservice;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mohamed.halim.dtos.MediaDto;
import com.mohamed.halim.dtos.MessageDto;

import lombok.AllArgsConstructor;

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
    public MediaDto postMedia(MultipartFile media) {
        return messageService.postMedia(media);
    }

    @PostMapping("/{to}")
    public List<MessageDto> getUserConversation(@PathVariable String to,  @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        return messageService.getUserConversation(auth, to);
    }

}

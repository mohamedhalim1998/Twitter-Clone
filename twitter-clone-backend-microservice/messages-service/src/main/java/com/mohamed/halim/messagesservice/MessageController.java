package com.mohamed.halim.messagesservice;

import java.security.Principal;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<MessageDto> getUserConversation(@PathVariable String to, Principal principal) {
        return messageService.getUserConversation(principal.getName(), to);
    }

}

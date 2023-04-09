package com.mohamed.halim.twitterclone.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import com.mohamed.halim.twitterclone.model.dto.NotificationDto;
import com.mohamed.halim.twitterclone.service.NotificationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class NotificationController {
    private NotificationService notificationService;

    @MessageMapping("/notification")
    public void handleNotification(@Payload NotificationDto notification) {
        log.info(notification.toString());
        notificationService.addNotification(notification);
    }
}

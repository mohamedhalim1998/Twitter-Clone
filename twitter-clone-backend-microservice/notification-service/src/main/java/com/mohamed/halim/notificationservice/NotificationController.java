package com.mohamed.halim.notificationservice;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import com.mohamed.halim.dtos.NotificationDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private NotificationService notificationService;

    @MessageMapping("/notification")
    public void handleNotification(@Payload NotificationDto notification) {
        log.info(notification.toString());
        notificationService.addNotification(notification);
    }
    @GetMapping
    public List<NotificationDto> getUserNotificatoins( @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        return notificationService.getUserNotificatoions(auth);
    }
    
}

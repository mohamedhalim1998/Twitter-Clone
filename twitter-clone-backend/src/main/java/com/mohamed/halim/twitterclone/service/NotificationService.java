package com.mohamed.halim.twitterclone.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.mohamed.halim.twitterclone.model.Notification;
import com.mohamed.halim.twitterclone.model.dto.NotificationDto;
import com.mohamed.halim.twitterclone.repository.NotificationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private SimpMessagingTemplate messagingTemplate;

    public void addNotification(NotificationDto dto) {
        Notification notification = notificationRepository.save(dto.toNotification());

        messagingTemplate.convertAndSend("/notification/" + notification.getTo(), notification);
    }

}

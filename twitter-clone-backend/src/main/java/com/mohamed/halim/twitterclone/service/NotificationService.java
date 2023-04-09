package com.mohamed.halim.twitterclone.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
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

    public List<NotificationDto> getUserNotificatoions(String username) {
        return notificationRepository
                .findAllByToOrderByTimeAsec(username, PageRequest.of(0, 30))
                .stream()
                .map(NotificationDto::fromEntity)
                .toList();
    }

}

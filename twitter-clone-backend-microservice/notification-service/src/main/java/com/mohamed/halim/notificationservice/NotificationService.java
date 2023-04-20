package com.mohamed.halim.notificationservice;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.mohamed.halim.dtos.NotificationDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private SimpMessagingTemplate messagingTemplate;

    public void addNotification(NotificationDto dto) {
        Notification notification = notificationRepository.save(Notification.fromDto(dto));

        messagingTemplate.convertAndSend("/notification/" + notification.getTo(), notification);
    }

    public List<NotificationDto> getUserNotificatoions(String username) {
        return notificationRepository
                .findAllByToOrderByTimeDesc(username, PageRequest.of(0, 30))
                .stream()
                .map(notification -> notification.toDto())
                .toList();
    }

}

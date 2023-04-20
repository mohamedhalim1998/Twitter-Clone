package com.mohamed.halim.notificationservice;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
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
    private RabbitTemplate rabbit;

    public void addNotification(NotificationDto dto) {
        Notification notification = notificationRepository.save(Notification.fromDto(dto));

        messagingTemplate.convertAndSend("/notification/" + notification.getTo(), notification);
    }

    public List<NotificationDto> getUserNotificatoions(String authHeader) {
        String username = rabbit.convertSendAndReceiveAsType("jwt", "jwt.token.extract.username",
                authHeader.substring(7), new ParameterizedTypeReference<String>() {
                });
        return notificationRepository
                .findAllByToOrderByTimeDesc(username, PageRequest.of(0, 30))
                .stream()
                .map(notification -> notification.toDto())
                .toList();
    }

}

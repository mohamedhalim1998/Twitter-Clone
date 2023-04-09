package com.mohamed.halim.twitterclone.model.dto;

import java.time.LocalDateTime;

import com.mohamed.halim.twitterclone.model.Notification;
import com.mohamed.halim.twitterclone.model.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    private Long id;
    private String from;
    private String to;
    private Long tweetId;
    private String text;
    private LocalDateTime time;
    private String type;

    public static NotificationDto fromEntity(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .from(notification.getFrom())
                .to(notification.getTo())
                .tweetId(notification.getTweetId())
                .text(notification.getText())
                .time(notification.getTime())
                .type(notification.getType().name())
                .build();
    }

    public Notification toEntity() {
        return Notification.builder()
                .id(id)
                .from(from)
                .to(to)
                .tweetId(tweetId)
                .text(text)
                .time(time)
                .type(NotificationType.valueOf(type))
                .build();
    }
}

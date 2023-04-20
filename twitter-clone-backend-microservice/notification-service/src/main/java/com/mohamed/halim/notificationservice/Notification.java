package com.mohamed.halim.notificationservice;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.mohamed.halim.dtos.NotificationDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "from_username")
    private String from;
    @Column(name = "to_username")
    private String to;
    private Long tweetId;
    private String text;
    private LocalDateTime time;
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    public static Notification fromDto(NotificationDto dto) {
        return Notification.builder()
                .id(dto.getId())
                .from(dto.getFrom())
                .to(dto.getTo())
                .tweetId(dto.getTweetId())
                .text(dto.getText())
                .time(LocalDateTime.now())
                .type(NotificationType.valueOf(dto.getType()))
                .build();

    }

    public NotificationDto toDto() {
        return NotificationDto.builder()
                .id(this.getId())
                .from(this.getFrom())
                .to(this.getTo())
                .tweetId(this.getTweetId())
                .text(this.getText())
                .time(this.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .type(this.getType().name())
                .build();
    }

}

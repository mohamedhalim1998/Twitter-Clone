package com.mohamed.halim.twitterclone.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Long id;
    private String from;
    private String to;
    private Long tweetId;
    private String text;
    private LocalDateTime time;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
}

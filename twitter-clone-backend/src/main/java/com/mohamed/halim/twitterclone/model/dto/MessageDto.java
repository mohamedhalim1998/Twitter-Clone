package com.mohamed.halim.twitterclone.model.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.mohamed.halim.twitterclone.model.Message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {
    private Long id;
    private String from;
    private String to;
    private String text;
    private MediaDto media;
    private long time;

    public static MessageDto fromMessage(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .from(message.getFrom())
                .to(message.getTo())
                .text(message.getText())
                .time(message.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();
    }

    public Message toMessage() {
        return Message.builder()
                .id(this.id)
                .from(this.from)
                .to(this.to)
                .text(this.text)
                .mediaId(this.media.getId())
                .time(LocalDateTime.now())
                .build();
    }
}

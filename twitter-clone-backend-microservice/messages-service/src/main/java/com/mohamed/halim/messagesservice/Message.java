package com.mohamed.halim.messagesservice;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.mohamed.halim.dtos.MessageDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "from_username")
    private String from;
    @Column(name = "to_username")
    private String to;
    private String text;
    private LocalDateTime time;
    private Long mediaId;

    public static Message fromDto(MessageDto dto) {
        return Message.builder()
                .id(dto.getId())
                .from(dto.getFrom())
                .to(dto.getTo())
                .text(dto.getText())
                .mediaId(dto.getMedia() != null ? dto.getMedia().getId() : null)
                .time(LocalDateTime.now())
                .build();
    }
    public MessageDto toDto() {
        return MessageDto.builder()
                .id(this.getId())
                .from(this.getFrom())
                .to(this.getTo())
                .text(this.getText())
                .time(this.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();
    }
}

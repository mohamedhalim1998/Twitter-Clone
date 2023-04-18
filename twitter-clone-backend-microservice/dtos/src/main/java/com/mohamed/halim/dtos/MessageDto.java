package com.mohamed.halim.dtos;



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

}

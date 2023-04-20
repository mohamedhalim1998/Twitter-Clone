package com.mohamed.halim.dtos;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Long id;
    private String from;
    private String to;
    private String text;
    private MediaDto media;
    private long time;

}

package com.mohamed.halim.dtos;



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
    private long time;
    private String type;

}

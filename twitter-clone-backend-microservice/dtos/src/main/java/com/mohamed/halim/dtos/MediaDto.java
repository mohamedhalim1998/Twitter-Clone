package com.mohamed.halim.dtos;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {
    private Long id;
    private String type;
    private String url;
    private Long duration;
    private int height;
    private int width;
}

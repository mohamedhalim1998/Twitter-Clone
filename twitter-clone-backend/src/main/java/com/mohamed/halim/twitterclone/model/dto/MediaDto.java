package com.mohamed.halim.twitterclone.model.dto;

import com.mohamed.halim.twitterclone.model.Media;
import com.mohamed.halim.twitterclone.model.MediaDimension;
import com.mohamed.halim.twitterclone.model.MediaType;

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

    public static MediaDto fromMedia(Media media) {
        return new MediaDto(
                media.getId(),
                media.getType().toString(),
                media.getUrl(),
                media.getDuration(),
                media.getDimentions().getHeight(),
                media.getDimentions().getWidth()
        );
    }

    public Media toMedia() {
        Media media = new Media();
        media.setId(this.id);
        media.setType(MediaType.valueOf(this.type.toUpperCase()));
        media.setUrl(this.url);
        media.setDuration(this.duration);
        MediaDimension dimension = new MediaDimension();
        dimension.setHeight(this.height);
        dimension.setWidth(this.width);
        media.setDimentions(dimension);
        return media;
    }
}

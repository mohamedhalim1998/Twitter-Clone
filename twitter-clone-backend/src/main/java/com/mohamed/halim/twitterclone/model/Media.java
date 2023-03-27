package com.mohamed.halim.twitterclone.model;

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
@Builder(toBuilder = true)
@Entity
public class Media {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private MediaType type;
    private String url;
    private Long duration;
    private int height;
    private int width;
    
    
}

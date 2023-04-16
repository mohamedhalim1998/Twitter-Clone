package com.mohamed.halim.tweetservice.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetRefrence {
    @Enumerated(EnumType.STRING)
    private TweetRefrenceType refType;
    private Long refId;
}

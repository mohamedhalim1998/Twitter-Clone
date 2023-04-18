package com.mohamed.halim.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetRefrence {
    private TweetRefrenceType refType;
    private Long refId;
}

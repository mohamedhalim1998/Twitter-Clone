package com.mohamed.halim.dtos;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TweetDto {
    private Long id;
    private String text;
    private List<Integer> editHistory;
    private Attachment attachment;
    private String authorId;
    private Long conversationId;
    private long createdDate;
    private int likes;
    private int replays;
    private int retweet;
    private Includes includes;
    private TweetRefrence tweetRefrence;


}
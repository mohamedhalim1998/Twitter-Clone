package com.mohamed.halim.twitterclone.model.dto;

import java.time.ZoneId;
import java.util.List;

import com.mohamed.halim.twitterclone.model.Attachment;
import com.mohamed.halim.twitterclone.model.Tweet;
import com.mohamed.halim.twitterclone.model.TweetRefrence;

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

    public static TweetDto from(Tweet tweet) {
        return TweetDto.builder()
                .id(tweet.getId())
                .text(tweet.getText())
                .editHistory(tweet.getEditHistory())
                .attachment(tweet.getAttachment())
                .authorId(tweet.getAuthorId())
                .conversationId(tweet.getConversationId())
                .createdDate(
                        tweet.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .tweetRefrence(tweet.getTweetRefrence())
                .build();
    }

}
package com.mohamed.halim.tweetservice.model;

import java.time.LocalDateTime;
import java.util.List;

import com.mohamed.halim.dtos.Attachment;
import com.mohamed.halim.dtos.TweetRefrence;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String text;
    private List<Integer> editHistory;
    @Embedded
    private Attachment attachment;
    private String authorId;
    private Long conversationId;
    private LocalDateTime createdDate;
    @Embedded
    private TweetRefrence tweetRefrence;

}

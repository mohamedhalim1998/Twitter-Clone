package com.mohamed.halim.twitterclone.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
    private Long id;
    private String text;
    private List<Integer> editHistory;
    @Embedded
    private Attachment attachment;
    private String authorId;
    private Long conversationId;
    private LocalDateTime createdDate;
    private Long replayToId;

}

package com.mohamed.halim.twitterclone.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mohamed.halim.twitterclone.model.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    @Query("SELECT t FROM Tweet t WHERE t.text LIKE CONCAT('%', :query, '%')")
    List<Tweet> searchTweets(String query, PageRequest pageRequest);

    int countByConversationId(Long id);

    int countByAuthorId(String username);

}

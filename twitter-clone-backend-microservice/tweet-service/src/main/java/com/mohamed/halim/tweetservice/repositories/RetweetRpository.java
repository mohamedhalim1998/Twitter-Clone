package com.mohamed.halim.tweetservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.tweetservice.model.Retweet;

public interface RetweetRpository extends JpaRepository<Retweet, Long> {
    public int countByOriginalTweetId(long id);
}

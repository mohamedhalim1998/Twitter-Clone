package com.mohamed.halim.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Retweet;

public interface RetweetRpository extends JpaRepository<Retweet, Long> {
    public int countByOriginalTweeId(long id);
}

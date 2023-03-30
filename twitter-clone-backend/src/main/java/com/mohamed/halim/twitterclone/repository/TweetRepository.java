package com.mohamed.halim.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

}

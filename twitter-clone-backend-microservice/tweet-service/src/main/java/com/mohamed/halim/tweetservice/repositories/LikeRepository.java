package com.mohamed.halim.tweetservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.tweetservice.model.Like;


public interface LikeRepository extends JpaRepository<Like, Long> {

    int countLikeByTweetId(Long id);

}

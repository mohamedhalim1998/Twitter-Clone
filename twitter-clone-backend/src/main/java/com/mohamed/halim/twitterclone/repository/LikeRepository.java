package com.mohamed.halim.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Like;


public interface LikeRepository extends JpaRepository<Like, Long> {

    int countLikeByTweetId(Long id);

}

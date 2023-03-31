package com.mohamed.halim.twitterclone.service;

import org.springframework.stereotype.Service;

import com.mohamed.halim.twitterclone.repository.LikeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LikeService {
    private LikeRepository likeRepository;

    public int countTweetLikes(Long id) {
        return likeRepository.countLikeByTweetId(id);
    }
}

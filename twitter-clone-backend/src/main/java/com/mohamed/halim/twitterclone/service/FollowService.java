package com.mohamed.halim.twitterclone.service;

import org.springframework.stereotype.Service;

import com.mohamed.halim.twitterclone.repository.FollowRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FollowService {
    private FollowRepository followRepository;

    public int countUserFollower(String username) {
        return followRepository.countByFollowing(username);
    }

    public int countUserFollowing(String username) {
        return followRepository.countByFollower(username);
    }
    
    
}

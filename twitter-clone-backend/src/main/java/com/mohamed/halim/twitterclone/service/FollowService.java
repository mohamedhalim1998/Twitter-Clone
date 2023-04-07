package com.mohamed.halim.twitterclone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mohamed.halim.twitterclone.model.Follow;
import com.mohamed.halim.twitterclone.repository.FollowRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class FollowService {
    private FollowRepository followRepository;

    public int countUserFollower(String username) {
        return followRepository.countByFollowing(username);
    }

    public int countUserFollowing(String username) {
        return followRepository.countByFollower(username);
    }

    public Follow addFollow(String follower, String following) {
        return followRepository.save(Follow.builder().follower(follower).following(following).build());
    }

    public void removeFollow(String follower, String following) {
        followRepository.deleteByFollowerAndFollowing(follower, following);
    }

    public List<String> getUserFollowingNames(String username) {
        log.error(username + "called following");
        return followRepository.findAllByFollower(username).stream().map(f -> f.getFollowing())
                .collect(Collectors.toCollection(ArrayList::new));
    }

}

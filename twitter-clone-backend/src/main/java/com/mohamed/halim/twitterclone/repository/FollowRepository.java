package com.mohamed.halim.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.mohamed.halim.twitterclone.model.Follow;

import jakarta.transaction.Transactional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    int countByFollowing(String username);

    int countByFollower(String username);
    @Modifying
    @Transactional
    void deleteByFollowerAndFollowing(String follower, String following);

}

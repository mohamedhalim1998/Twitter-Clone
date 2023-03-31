package com.mohamed.halim.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    int countByFollowing(String username);

    int countByFollower(String username);

}

package com.mohamed.halim.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Poll;

public interface PollRepository extends JpaRepository<Poll, Long>{
    
}

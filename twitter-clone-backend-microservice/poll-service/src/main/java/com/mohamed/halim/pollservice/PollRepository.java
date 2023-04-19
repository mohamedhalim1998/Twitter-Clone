package com.mohamed.halim.pollservice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.pollservice.model.Poll;


public interface PollRepository extends JpaRepository<Poll, Long>{
    
}

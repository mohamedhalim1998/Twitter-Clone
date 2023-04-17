package com.mohamed.halim.profileservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.profileservice.model.Block;


public interface BlockRepository extends JpaRepository<Block, Long> {
    
}

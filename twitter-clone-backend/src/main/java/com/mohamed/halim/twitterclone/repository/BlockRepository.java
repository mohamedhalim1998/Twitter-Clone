package com.mohamed.halim.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Block;

public interface BlockRepository extends JpaRepository<Block, Long> {
    
}

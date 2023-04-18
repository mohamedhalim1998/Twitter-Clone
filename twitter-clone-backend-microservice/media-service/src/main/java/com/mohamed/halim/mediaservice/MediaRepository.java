package com.mohamed.halim.mediaservice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.mediaservice.model.Media;


public interface MediaRepository extends JpaRepository<Media, Long> {

}

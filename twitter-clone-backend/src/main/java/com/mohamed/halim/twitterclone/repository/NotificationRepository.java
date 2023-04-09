package com.mohamed.halim.twitterclone.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

    List<Notification> findAllByToOrderByTimeAsec(String username, Pageable pageable);
    
}

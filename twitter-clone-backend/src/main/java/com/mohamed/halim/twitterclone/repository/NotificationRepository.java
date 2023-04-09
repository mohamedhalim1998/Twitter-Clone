package com.mohamed.halim.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
}

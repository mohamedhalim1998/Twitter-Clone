package com.mohamed.halim.notificationservice;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Long>{

    List<Notification> findAllByToOrderByTimeDesc(String username, Pageable pageable);
    
}

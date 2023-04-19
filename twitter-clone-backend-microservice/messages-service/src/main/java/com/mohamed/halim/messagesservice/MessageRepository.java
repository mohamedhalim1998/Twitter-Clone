package com.mohamed.halim.messagesservice;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByFromAndToOrderByTimeDesc(String from, String to);

    List<Message> findAllByFromAndToOrderByTimeDesc(String from, String to, Pageable of);

}

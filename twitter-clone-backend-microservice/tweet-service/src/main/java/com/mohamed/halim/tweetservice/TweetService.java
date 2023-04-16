package com.mohamed.halim.tweetservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweetService {
    private TweetRepository tweetRepository;

    @RabbitListener(queues = "tweet.user.tweets.count")
    public int countUserTweets(String username) {
        return tweetRepository.countByAuthorId(username);
    }

}

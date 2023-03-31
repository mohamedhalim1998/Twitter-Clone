package com.mohamed.halim.twitterclone.service;

import org.springframework.stereotype.Service;

import com.mohamed.halim.twitterclone.repository.RetweetRpository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RetweetService {
    private RetweetRpository retweetRpository;

    public int countTweetRetweets(Long id) {
        return retweetRpository.countByOriginalTweeId(id);
    }

}

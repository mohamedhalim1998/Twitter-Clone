package com.mohamed.halim.tweetservice;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mohamed.halim.dtos.PollDto;
import com.mohamed.halim.dtos.TweetDto;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
    private TweetService tweetService;

    @PostMapping
    public TweetDto addTweet(@RequestPart(name = "tweet", required = true) TweetDto tweet,
            @RequestPart(name = "media", required = false) MultipartFile media,
            @RequestPart(name = "poll", required = false) PollDto poll,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        return tweetService.addTweet(tweet, media, poll, auth);
    }
    
    @GetMapping("/{id}")
    public TweetDto getTweet(@PathVariable Long id) {
        return tweetService.getTweet(id);
    }
}

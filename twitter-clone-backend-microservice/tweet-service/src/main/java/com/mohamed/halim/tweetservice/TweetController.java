package com.mohamed.halim.tweetservice;

import java.util.List;

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

    @GetMapping("/feed")
    public List<TweetDto> getUserFeed(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        return tweetService.getUserFeed(auth);
    }

    @GetMapping("/user/{username}")
    public List<TweetDto> getUserTweets(@PathVariable String username) {
        return tweetService.getUserTweets(username);
    }

    @PostMapping("/retweet/{id}")
    public TweetDto retweet(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        return tweetService.retweet(id, auth);

    }

    @PostMapping("replay/{id}")
    public TweetDto replayToTweet(
            @PathVariable Long id,
            @RequestPart(name = "tweet", required = true) TweetDto tweet,
            @RequestPart(name = "media", required = false) MultipartFile media,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        return tweetService.replayToTweet(id, tweet, media, auth);
    }

    @PostMapping("like/{id}")
    public void likeTweet(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        tweetService.likeTweet(id, auth);
    }

}

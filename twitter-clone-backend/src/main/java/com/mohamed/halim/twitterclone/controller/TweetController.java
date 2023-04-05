package com.mohamed.halim.twitterclone.controller;

import java.io.IOException;
import java.security.Principal;


import org.apache.tika.exception.TikaException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.mohamed.halim.twitterclone.model.dto.PollDto;
import com.mohamed.halim.twitterclone.model.dto.TweetDto;
import com.mohamed.halim.twitterclone.service.TweetService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/tweets")
public class TweetController {
    private final TweetService tweetService;

    @PostMapping
    public TweetDto addTweet(@RequestPart(name = "tweet", required = true) TweetDto tweet,
            @RequestPart(name = "media", required = false) MultipartFile media,
            @RequestPart(name = "poll", required = false) PollDto poll,
            Principal principal) throws IllegalStateException, IOException, SAXException, TikaException {
        return tweetService.addTweet(tweet, media, poll, principal.getName());
    }

    @GetMapping("/{id}")
    public TweetDto getTweet(@PathVariable Long id) {
        return tweetService.getTweet(id);
    }
}

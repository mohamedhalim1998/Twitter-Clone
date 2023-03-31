package com.mohamed.halim.twitterclone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mohamed.halim.twitterclone.model.dto.ProfileDto;
import com.mohamed.halim.twitterclone.model.dto.SearchResult;
import com.mohamed.halim.twitterclone.model.dto.TweetDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SearchService {
    ProfileService profileService;
    TweetService tweetService;

    public SearchResult search(String query, String filter) {
        if (filter == null) {
            return searchAll(query);
        } else if (filter.equals("users")) {
            return searchUsers(query);
        } else {
            return searchTweets(query);

        }

    }

    private SearchResult searchAll(String query) {
        List<TweetDto> tweets = tweetService.searchTweets(query);
        List<ProfileDto> users = profileService.searchProfiles(query);
        return new SearchResult(users, tweets);
    }

    private SearchResult searchUsers(String query) {
        List<TweetDto> tweets = tweetService.searchTweets(query);
        return SearchResult.builder().tweets(tweets).build();
    }

    private SearchResult searchTweets(String query) {
        List<ProfileDto> users = profileService.searchProfiles(query);
        return SearchResult.builder().user(users).build();
    }
}

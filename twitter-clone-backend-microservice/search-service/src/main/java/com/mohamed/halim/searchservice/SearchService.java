package com.mohamed.halim.searchservice;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.mohamed.halim.dtos.ProfileDto;
import com.mohamed.halim.dtos.SearchResult;
import com.mohamed.halim.dtos.TweetDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SearchService {
    private RabbitTemplate rabbit;

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
        List<TweetDto> tweets = rabbit.convertSendAndReceiveAsType("tweet", "tweet.search", query,
                new ParameterizedTypeReference<List<TweetDto>>() {
                });
        List<ProfileDto> users =  rabbit.convertSendAndReceiveAsType("profile", "profile.search", query,
        new ParameterizedTypeReference<List<ProfileDto>>() {
        });
        return new SearchResult(users, tweets);
    }

    private SearchResult searchUsers(String query) {
        List<TweetDto> tweets = rabbit.convertSendAndReceiveAsType("tweet", "tweet.search", query,
                new ParameterizedTypeReference<List<TweetDto>>() {
                });
        return SearchResult.builder().tweets(tweets).build();
    }

    private SearchResult searchTweets(String query) {
        List<ProfileDto> users =  rabbit.convertSendAndReceiveAsType("profile", "profile.search", query,
        new ParameterizedTypeReference<List<ProfileDto>>() {
        });
        return SearchResult.builder().user(users).build();
    }
}

package com.mohamed.halim.twitterclone.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Includes {
    private List<ProfileDto> users = new ArrayList<>();
    private List<TweetDto> tweets = new ArrayList<>();
    private List<PollDto> polls = new ArrayList<>();
    private List<MediaDto> media = new ArrayList<>();

    public static class Builder {
        private Includes includes = new Includes();

        public Builder addUser(ProfileDto user) {
            includes.users.add(user);
            return this;
        }

        public Builder addTweet(TweetDto tweet) {
            includes.tweets.add(tweet);
            return this;
        }

        public Builder addPoll(PollDto poll) {
            includes.polls.add(poll);
            return this;
        }

        public Builder addMedia(MediaDto media) {
            includes.media.add(media);
            return this;
        }

        public Includes build() {
            return includes;
        }
    }

}

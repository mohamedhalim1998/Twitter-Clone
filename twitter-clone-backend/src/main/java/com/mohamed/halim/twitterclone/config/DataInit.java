package com.mohamed.halim.twitterclone.config;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mohamed.halim.twitterclone.model.Profile;
import com.mohamed.halim.twitterclone.model.Tweet;
import com.mohamed.halim.twitterclone.repository.ProfileRepository;
import com.mohamed.halim.twitterclone.repository.TweetRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class DataInit implements CommandLineRunner {
    private PasswordEncoder encoder;
    private ProfileRepository profileRepository;
    private TweetRepository tweetRepository;

    @Override
    public void run(String... args) throws Exception {
        Profile profile = Profile.builder().username("user1")
                .email("a@a.com")
                .createdAt(new Date())
                .password(encoder.encode("123"))
                .build();
        profileRepository.save(profile);
        for (int i = 0; i < 100; i++) {
            tweetRepository.save(Tweet.builder().text("Tweet from user1 id: " + i).createdDate(LocalDateTime.now())
                    .authorId("user1").build());
        }

    }

}

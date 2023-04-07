package com.mohamed.halim.twitterclone.config;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mohamed.halim.twitterclone.model.Attachment;
import com.mohamed.halim.twitterclone.model.AttachmentType;
import com.mohamed.halim.twitterclone.model.Follow;
import com.mohamed.halim.twitterclone.model.Media;
import com.mohamed.halim.twitterclone.model.MediaDimension;
import com.mohamed.halim.twitterclone.model.MediaType;
import com.mohamed.halim.twitterclone.model.Profile;
import com.mohamed.halim.twitterclone.model.Tweet;
import com.mohamed.halim.twitterclone.model.TweetRefrence;
import com.mohamed.halim.twitterclone.model.TweetRefrenceType;
import com.mohamed.halim.twitterclone.repository.FollowRepository;
import com.mohamed.halim.twitterclone.repository.MediaRepository;
import com.mohamed.halim.twitterclone.repository.ProfileRepository;
import com.mohamed.halim.twitterclone.repository.TweetRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class DataInit implements CommandLineRunner {
    private PasswordEncoder encoder;
    private ProfileRepository profileRepository;
    private TweetRepository tweetRepository;
    private MediaRepository mediaRepository;
    private FollowRepository followRepository;

    @Override
    public void run(String... args) throws Exception {
        Profile profile = Profile.builder().username("user1")
                .fullname("Mohamed Halim")
                .profileImageUrl("http://localhost:8080/api/v1/media/default_profile.png")
                .coverImageUrl("http://localhost:8080/api/v1/media/default_cover.jpg")
                .bio("this my user bio Ullamco duis ex proident dolore magna tempor culpa.")
                .email("a@a.com")
                .createdAt(new Date())
                .password(encoder.encode("123"))
                .build();
        Profile profile2 = Profile.builder().username("user2")
                .fullname("You-Know-Who")
                .profileImageUrl("http://localhost:8080/api/v1/media/default_profile.png")
                .coverImageUrl("http://localhost:8080/api/v1/media/default_cover.jpg")
                .bio("this my user bio Ullamco duis ex proident dolore magna tempor culpa.")
                .email("a@a.com")
                .createdAt(new Date())
                .password(encoder.encode("123"))
                .build();
        Tweet tweet = Tweet.builder().text("this my pinned tweet").createdDate(LocalDateTime.now())
                .authorId("user1").build();
        tweet = tweetRepository.save(tweet);
        profile.setPinnedTweetId(tweet.getId());
        profileRepository.save(profile);
        profileRepository.save(profile2);
        for (int i = 0; i < 100; i++) {
            tweetRepository.save(Tweet.builder().text("Tweet from user1 id: " + i)
                    .createdDate(LocalDateTime.now())
                    .authorId("user1").build());
        }
        for (int i = 0; i < 100; i++) {
            tweetRepository.save(Tweet.builder().text("Tweet from user2 id: " + i)
                    .createdDate(LocalDateTime.now())
                    .authorId("user2").build());
        }

        Media media = mediaRepository.save(
                Media.builder()
                        .url("http://localhost:8080/api/v1/media/default_profile.png")
                        .type(MediaType.IMAGE)
                        .dimentions(new MediaDimension(1920, 1080))
                        .build());
        tweetRepository.save(
                Tweet.builder().text("Tweet from user1 with media").createdDate(LocalDateTime.now())
                        .attachment(new Attachment(AttachmentType.MEDIA, media.getId()))
                        .authorId("user1").build());
        tweetRepository.save(Tweet.builder().text("Tweet from user1 with media and is retweet")
                .createdDate(LocalDateTime.now())
                .attachment(new Attachment(AttachmentType.MEDIA, media.getId()))
                .tweetRefrence(new TweetRefrence(TweetRefrenceType.RETWEET, 1L))
                .authorId("user2").build());

                followRepository.save(new Follow(null, "user2", "user1"));

    }

}

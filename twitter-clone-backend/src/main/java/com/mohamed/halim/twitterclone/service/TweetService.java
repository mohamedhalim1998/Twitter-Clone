package com.mohamed.halim.twitterclone.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.mohamed.halim.twitterclone.model.Attachment;
import com.mohamed.halim.twitterclone.model.AttachmentType;
import com.mohamed.halim.twitterclone.model.Tweet;
import com.mohamed.halim.twitterclone.model.TweetRefrence;
import com.mohamed.halim.twitterclone.model.TweetRefrenceType;
import com.mohamed.halim.twitterclone.model.dto.Includes;
import com.mohamed.halim.twitterclone.model.dto.PollDto;
import com.mohamed.halim.twitterclone.model.dto.TweetDto;
import com.mohamed.halim.twitterclone.repository.TweetRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class TweetService {
    private TweetRepository tweetRepository;
    private MediaService mediaService;
    private PollService pollService;
    private LikeService likeService;
    private RetweetService retweetService;
    private ProfileService profileService;
    private FollowService followService;

    public TweetDto addTweet(TweetDto dto, MultipartFile media, PollDto poll, String username)
            throws IllegalStateException, IOException, SAXException, TikaException {
        Attachment attachment = null;
        if (media != null) {
            long mediaId = mediaService.saveMedia(media);
            attachment = new Attachment(AttachmentType.MEDIA, mediaId);
        } else if (poll != null) {
            long pollId = pollService.addPoll(poll);
            attachment = new Attachment(AttachmentType.POLL, pollId);

        }

        Tweet tweet = Tweet.builder().text(dto.getText())
                .authorId(username)
                .attachment(attachment)
                .createdDate(LocalDateTime.now())
                .tweetRefrence(dto.getTweetRefrence())
                .build();

        return convertToDto(tweetRepository.save(tweet), true);
    }

    public TweetDto getTweet(Long id) {
        return tweetRepository.findById(id).map(t -> convertToDto(t, true)).get();
    }

    public List<TweetDto> searchTweets(String query) {
        return tweetRepository.searchTweets(query, PageRequest.of(0, 20)).stream().map(this::convertToDto).toList();
    }

    private TweetDto convertToDto(Tweet tweet) {
        return convertToDto(tweet, false);
    }

    private TweetDto convertToDto(Tweet tweet, boolean withIcludes) {
        TweetDto dto = TweetDto.from(tweet);
        dto.setLikes(likeService.countTweetLikes(dto.getId()));
        dto.setRetweet(retweetService.countTweetRetweets(dto.getId()));
        dto.setReplays(tweetRepository.countByConversationId(dto.getId()));
        if (withIcludes) {
            Includes includes = getIncudes(tweet);
            dto.setIncludes(includes);
        }
        return dto;
    }

    private Includes getIncudes(Tweet tweet) {
        Includes.Builder builder = new Includes.Builder();
        builder.addUser(profileService.getProfile(tweet.getAuthorId()));
        addMediaToIncludes(tweet, builder);
        addPollToIncludes(tweet, builder);
        addRefTweetToIncludes(tweet, builder);
        return builder.build();
    }

    private void addRefTweetToIncludes(Tweet tweet, Includes.Builder builder) {
        if (tweet.getTweetRefrence() != null) {
            Optional<Tweet> refTweet = tweetRepository.findById(tweet.getTweetRefrence().getRefId());
            addMediaToIncludes(refTweet.get(), builder);
            addPollToIncludes(refTweet.get(), builder);
            builder.addTweet(refTweet.map(this::convertToDto).get())
                    .addUser(profileService.getProfile(refTweet.get().getAuthorId()));

        }
    }

    private void addMediaToIncludes(Tweet tweet, Includes.Builder builder) {
        if (tweet.getAttachment() != null && tweet.getAttachment().getType() == AttachmentType.MEDIA) {
            builder.addMedia(mediaService.getMedia(tweet.getAttachment().getAttacmentId()));
        }
    }

    private void addPollToIncludes(Tweet tweet, Includes.Builder builder) {
        if (tweet.getAttachment() != null && tweet.getAttachment().getType() == AttachmentType.POLL) {
            builder.addPoll(pollService.getPoll(tweet.getAttachment().getAttacmentId()));
        }
    }

    public List<TweetDto> getUserFeed(String username) {
        List<String> following = followService.getUserFollowingNames(username);
        log.info(following.toString());
        following.add(username);
        log.info(following.toString());
        return tweetRepository.findAllByAuthorIdInOrderByCreatedDateDesc(following, PageRequest.of(0, 50)).stream()
                .map(t -> convertToDto(t, true)).toList();
    }

    public List<TweetDto> getUserTweets(String username) {
        return tweetRepository.findAllByAuthorIdOrderByCreatedDateDesc(username, PageRequest.of(0, 50)).stream()
                .map(t -> convertToDto(t, true)).toList();
    }

    public TweetDto retweet(Long id, String name) {
        Tweet tweet = tweetRepository.findById(id).get()
                .toBuilder()
                .id(null)
                .tweetRefrence(new TweetRefrence(TweetRefrenceType.RETWEET, id))
                .authorId(name)
                .createdDate(LocalDateTime.now())
                .build();

        tweet = tweetRepository.save(tweet);
        return convertToDto(tweet, true);
    }

    public TweetDto replayToTweet(Long id, TweetDto dto, MultipartFile media, String username)
            throws IllegalStateException, IOException, SAXException, TikaException {
        Attachment attachment = null;
        log.info("media : " + media);
        if (media != null) {
            long mediaId = mediaService.saveMedia(media);
            attachment = new Attachment(AttachmentType.MEDIA, mediaId);
        }
        Tweet tweet = Tweet.builder().text(dto.getText())
                .authorId(username)
                .attachment(attachment)
                .conversationId(id)
                .tweetRefrence(new TweetRefrence(TweetRefrenceType.REPLAY, id))
                .createdDate(LocalDateTime.now())
                .build();

        return convertToDto(tweetRepository.save(tweet), true);
    }

    public void likeTweet(Long id, String username) {
        likeService.likeTweet(id, username);
    }

}

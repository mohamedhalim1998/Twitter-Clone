package com.mohamed.halim.twitterclone.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.mohamed.halim.twitterclone.model.Attachment;
import com.mohamed.halim.twitterclone.model.AttachmentType;
import com.mohamed.halim.twitterclone.model.Tweet;
import com.mohamed.halim.twitterclone.model.dto.Includes;
import com.mohamed.halim.twitterclone.model.dto.PollDto;
import com.mohamed.halim.twitterclone.model.dto.TweetDto;
import com.mohamed.halim.twitterclone.repository.TweetRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweetService {
    private TweetRepository tweetRepository;
    private MediaService mediaService;
    private PollService pollService;
    private LikeService likeService;
    private RetweetService retweetService;
    private ProfileService profileService;

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
                .conversationId(dto.getConversationId())
                .createdDate(LocalDateTime.now())
                .tweetRefrence(dto.getTweetRefrence())
                .build();

        return TweetDto.from(tweetRepository.save(tweet));
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
        if (tweet.getAttachment() != null && tweet.getAttachment().getType() == AttachmentType.MEDIA) {
            builder.addMedia(mediaService.getMedia(tweet.getAttachment().getAttacmentId()));
        }
        if (tweet.getAttachment() != null && tweet.getAttachment().getType() == AttachmentType.POLL) {
            builder.addPoll(pollService.getPoll(tweet.getAttachment().getAttacmentId()));
        }
        builder.addUser(profileService.getProfile(tweet.getAuthorId()));
        if (tweet.getTweetRefrence() != null) {
            TweetDto refTweet = tweetRepository.findById(tweet.getTweetRefrence().getRefId()).map(this::convertToDto)
                    .get();
            builder.addTweet(refTweet).addUser(profileService.getProfile(refTweet.getAuthorId()));
        }
        return builder.build();
    }

}

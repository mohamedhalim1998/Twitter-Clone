package com.mohamed.halim.tweetservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mohamed.halim.dtos.Attachment;
import com.mohamed.halim.dtos.AttachmentType;
import com.mohamed.halim.dtos.Includes;
import com.mohamed.halim.dtos.MediaDto;
import com.mohamed.halim.dtos.PollDto;
import com.mohamed.halim.dtos.ProfileDto;
import com.mohamed.halim.dtos.TweetDto;
import com.mohamed.halim.tweetservice.model.Tweet;
import com.mohamed.halim.tweetservice.repositories.LikeRepository;
import com.mohamed.halim.tweetservice.repositories.RetweetRpository;
import com.mohamed.halim.tweetservice.repositories.TweetRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweetService {
    private TweetRepository tweetRepository;
    private RestTemplate restTemplate;
    private RabbitTemplate rabbit;
    private LikeRepository likeRepository;
    private RetweetRpository retweetRpository;

    @RabbitListener(queues = "tweet.user.tweets.count")
    public int countUserTweets(String username) {
        return tweetRepository.countByAuthorId(username);
    }

    public TweetDto addTweet(TweetDto dto, MultipartFile media, PollDto poll, String authHeader) {
        Attachment attachment = null;
        if (media != null) {
            long mediaId = postMedia(media).getId();
            attachment = new Attachment(AttachmentType.MEDIA, mediaId);
        } else if (poll != null) {
            PollDto pollDto = rabbit.convertSendAndReceiveAsType("poll", "poll.add", poll,
                    new ParameterizedTypeReference<PollDto>() {
                    });
            attachment = new Attachment(AttachmentType.POLL, pollDto.getId());

        }
        String username = rabbit.convertSendAndReceiveAsType("jwt", "jwt.token.extract.username",
                authHeader.substring(7), new ParameterizedTypeReference<String>() {
                });
        Tweet tweet = Tweet.builder().text(dto.getText())
                .authorId(username)
                .attachment(attachment)
                .createdDate(LocalDateTime.now())
                .tweetRefrence(dto.getTweetRefrence())
                .build();

        return convertToDto(tweetRepository.save(tweet), true);
    }

    private MediaDto postMedia(MultipartFile profileImage) {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("media", profileImage.getResource());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MediaDto media = restTemplate.postForObject("lb://media-service/media",
                new HttpEntity<>(requestBody, headers),
                MediaDto.class);
        return media;
    }

    private TweetDto convertToDto(Tweet tweet) {
        return convertToDto(tweet, false);
    }

    private TweetDto convertToDto(Tweet tweet, boolean withIcludes) {
        TweetDto dto = TweetDto.builder()
                .id(tweet.getId())
                .text(tweet.getText())
                .editHistory(tweet.getEditHistory())
                .attachment(tweet.getAttachment())
                .authorId(tweet.getAuthorId())
                .conversationId(tweet.getConversationId())
                .createdDate(
                        tweet.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .tweetRefrence(tweet.getTweetRefrence())
                .build();
        dto.setLikes(likeRepository.countLikeByTweetId(dto.getId()));
        dto.setRetweet(retweetRpository.countByOriginalTweetId(dto.getId()));
        dto.setReplays(tweetRepository.countByConversationId(dto.getId()));
        if (withIcludes) {
            Includes includes = getIncudes(tweet);
            dto.setIncludes(includes);
        }
        return dto;
    }

    private Includes getIncudes(Tweet tweet) {
        Includes.Builder builder = new Includes.Builder();
        ProfileDto profile = rabbit.convertSendAndReceiveAsType("profile", "profile.load.profile.dto",
                tweet.getAuthorId(), new ParameterizedTypeReference<ProfileDto>() {
                });
        builder.addUser(profile);
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
            ProfileDto refProfile = rabbit.convertSendAndReceiveAsType("profile", "profile.load.profile.dto",
                    refTweet.get().getAuthorId(), new ParameterizedTypeReference<ProfileDto>() {
                    });
            builder.addTweet(refTweet.map(this::convertToDto).get())
                    .addUser(refProfile);

        }
    }

    private void addMediaToIncludes(Tweet tweet, Includes.Builder builder) {
        if (tweet.getAttachment() != null && tweet.getAttachment().getType() == AttachmentType.MEDIA) {
            MediaDto media = rabbit.convertSendAndReceiveAsType("media", "media.get",
                    tweet.getAttachment().getAttacmentId(), new ParameterizedTypeReference<MediaDto>() {
                    });
            builder.addMedia(media);
        }
    }

    private void addPollToIncludes(Tweet tweet, Includes.Builder builder) {
        if (tweet.getAttachment() != null && tweet.getAttachment().getType() == AttachmentType.POLL) {
            PollDto poll = rabbit.convertSendAndReceiveAsType("poll", "poll.get",
                    tweet.getAttachment().getAttacmentId(), new ParameterizedTypeReference<PollDto>() {
                    });
            builder.addPoll(poll);
        }
    }

}

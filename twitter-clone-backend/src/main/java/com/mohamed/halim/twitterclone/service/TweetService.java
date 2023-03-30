package com.mohamed.halim.twitterclone.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.mohamed.halim.twitterclone.model.Attachment;
import com.mohamed.halim.twitterclone.model.AttachmentType;
import com.mohamed.halim.twitterclone.model.Tweet;
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
                .replayToId(dto.getReplyToId())
                .build();
        return TweetDto.from(tweetRepository.save(tweet));
    }

}

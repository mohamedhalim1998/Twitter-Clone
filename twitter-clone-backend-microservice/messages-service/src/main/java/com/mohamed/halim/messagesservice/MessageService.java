package com.mohamed.halim.messagesservice;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mohamed.halim.dtos.MediaDto;
import com.mohamed.halim.dtos.MessageDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private SimpMessagingTemplate messagingTemplate;
    private RestTemplate restTemplate;
    private RabbitTemplate rabbit;

    public void handelMessage(MessageDto dto) {
        Message message = messageRepository.save(Message.fromDto(dto));
        messagingTemplate.convertAndSend("/message/" + message.getTo(), message);
    }

    public List<MessageDto> getUserConversation(String authHeader, String to) {
        String from = rabbit.convertSendAndReceiveAsType("jwt", "jwt.token.extract.username",
                authHeader.substring(7), new ParameterizedTypeReference<String>() {
                });
        return messageRepository.findAllByFromAndToOrderByTimeDesc(from, to, PageRequest.of(0, 20)).stream()
                .map(message -> {
                    MessageDto dto = message.toDto();
                    if (message.getMediaId() != null) {
                        MediaDto media = rabbit.convertSendAndReceiveAsType("media", "media.get",
                                message.getMediaId(), new ParameterizedTypeReference<MediaDto>() {
                                });
                        dto.setMedia(media);
                    }
                    return dto;
                }).toList();
    }

    public MediaDto postMedia(MultipartFile profileImage) {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("media", profileImage.getResource());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MediaDto media = restTemplate.postForObject("lb://media-service/media",
                new HttpEntity<>(requestBody, headers),
                MediaDto.class);
        return media;
    }

}

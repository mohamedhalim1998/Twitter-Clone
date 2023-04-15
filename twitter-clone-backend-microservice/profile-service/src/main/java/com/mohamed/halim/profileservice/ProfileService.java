package com.mohamed.halim.profileservice;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import com.mohamed.halim.profileservice.model.AuthResponse;
import com.mohamed.halim.profileservice.model.Profile;
import com.mohamed.halim.profileservice.model.RegisterDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileService {
    private ProfileRepository profileRepository;
    private RabbitTemplate rabbit;
    private MessageConverter converter;

    @RabbitListener(queues = "register-user")
    public void registerUser(RegisterDto dto, Message message) throws IOException {
        MessageProperties props = message.getMessageProperties();
        if (profileRepository.findByEmail(dto.getEmail()).isPresent()) {
            props.setHeader("error", "Email used before");
            rabbit.send(props.getReplyTo(), converter.toMessage("", props));
            return;
        }
        if (profileRepository.findByUsername(dto.getUsername()).isPresent()) {
            props.setHeader("error", "username used before");
            rabbit.send(props.getReplyTo(), converter.toMessage("", props));
            return;
        }
        Profile saved = profileRepository.save(dto.toProfile());
        props.setContentType("application/json");
        rabbit.send(props.getReplyTo(), converter.toMessage(buildAuthResponse(saved), props));
    }

    @RabbitListener(queues = "profile.load.profile")
    public Profile getProfile(String username) {
        return profileRepository.findByUsername(username).get();
    }

    private AuthResponse buildAuthResponse(Profile profile) {
        Message message = rabbit.sendAndReceive("jwt",
                "jwt.token.generate",
                converter.toMessage(profile, null));
        return AuthResponse.builder()
                .username(profile.getUsername())
                .email(profile.getEmail())
                .token(new String(message.getBody()))
                .build();
    }

}

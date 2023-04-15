package com.mohamed.halim.profileservice;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.mohamed.halim.profileservice.model.AuthResponse;
import com.mohamed.halim.profileservice.model.LoginDto;
import com.mohamed.halim.profileservice.model.PasswordValidation;
import com.mohamed.halim.profileservice.model.Profile;
import com.mohamed.halim.profileservice.model.RegisterDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileService {
    private ProfileRepository profileRepository;
    private RabbitTemplate rabbit;
    private MessageConverter converter;

    @RabbitListener(queues = "profile.user.register")
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
        String token = (String) converter.fromMessage(message);
        return AuthResponse.builder()
                .username(profile.getUsername())
                .email(profile.getEmail())
                .token(token)
                .build();
    }

    @RabbitListener(queues = "profile.user.login")
    public void login(LoginDto dto, Message message) {
        MessageProperties props = message.getMessageProperties();
        var profileOptional = profileRepository.findByUsername(dto.getUsername());
        if (profileOptional.isEmpty()) {
            profileOptional = profileRepository.findByEmail(dto.getUsername());
        }
        if (profileOptional.isPresent()
                && checkPassword(dto.getPassword(), profileOptional.get().getPassword())) {
            rabbit.send(props.getReplyTo(),
                    converter.toMessage(buildAuthResponse(profileOptional.get()), props));
            return;
        }
        props.setHeader("error", "username or password is incorrect");
        rabbit.send(props.getReplyTo(), converter.toMessage("", props));
    }

    private boolean checkPassword(String password, String hash) {
        return rabbit.convertSendAndReceiveAsType("auth", "auth.password.validation",
                new PasswordValidation(password, hash), new ParameterizedTypeReference<Boolean>() {
                });
    }

}

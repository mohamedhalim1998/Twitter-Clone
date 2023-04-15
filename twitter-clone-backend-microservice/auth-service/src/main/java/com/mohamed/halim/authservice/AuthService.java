package com.mohamed.halim.authservice;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mohamed.halim.authservice.model.AuthResponse;
import com.mohamed.halim.authservice.model.LoginDto;
import com.mohamed.halim.authservice.model.PasswordValidation;
import com.mohamed.halim.authservice.model.RegisterDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class AuthService {
    private RabbitTemplate rabbit;
    private PasswordEncoder passwordEncoder;
    private Jackson2JsonMessageConverter converter;

    public AuthResponse registerUser(RegisterDto dto) {
        log.info(dto.toString());
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Message message = rabbit.sendAndReceive("profile", "profile.user.register", converter.toMessage(dto, null));
        String error = message.getMessageProperties().getHeader("error");
        if (error != null) {
            throw new RuntimeException(error);
        }
        log.info(message.toString());
        log.info(new String(message.getBody()));
        return (AuthResponse) converter.fromMessage(message, new ParameterizedTypeReference<AuthResponse>() {
        });
    }

    public AuthResponse login(LoginDto dto) {
        Message message = rabbit.sendAndReceive("profile", "profile.user.login", converter.toMessage(dto, null));
        String error = message.getMessageProperties().getHeader("error");
        if (error != null) {
            throw new RuntimeException(error);
        }
        log.info(message.toString());
        log.info(new String(message.getBody()));
        return (AuthResponse) converter.fromMessage(message, new ParameterizedTypeReference<AuthResponse>() {
        });
    }

    @RabbitListener(queues = "auth.password.validation")
    public boolean validatePassword(PasswordValidation password) {
        return passwordEncoder.matches(password.getPassword(), password.getHash());

    }

    public void verifyToken(String token) {
        Message message = rabbit.sendAndReceive("jwt", "jwt.token.verify", converter.toMessage(token, null));
        String error = message.getMessageProperties().getHeader("error");
        if (error != null) {
            throw new RuntimeException(error);
        }
    }

}

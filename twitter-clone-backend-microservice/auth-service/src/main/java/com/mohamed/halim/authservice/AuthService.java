package com.mohamed.halim.authservice;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.mohamed.halim.authservice.model.AuthResponse;
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

    public AuthResponse registerUser(RegisterDto dto) throws StreamReadException, DatabindException, IOException {
        log.info(dto.toString());
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Message message = rabbit.sendAndReceive("profile", "register-user", converter.toMessage(dto, null));
        String error = message.getMessageProperties().getHeader("error");
        if (error != null) {
            throw new RuntimeException(error);
        }
        log.info(message.toString());
        log.info(new String(message.getBody()));
        return (AuthResponse) converter.fromMessage(message, new ParameterizedTypeReference<AuthResponse>(){});
    }

}

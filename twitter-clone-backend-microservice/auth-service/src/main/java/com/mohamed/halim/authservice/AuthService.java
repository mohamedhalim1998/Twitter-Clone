package com.mohamed.halim.authservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthResponse registerUser(RegisterDto dto) {
        log.info(dto.toString());
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        AuthResponse authResponse = rabbit.convertSendAndReceiveAsType("profile", "register-user", dto,
                new ParameterizedTypeReference<AuthResponse>() {

                });
        return authResponse;
    }

}

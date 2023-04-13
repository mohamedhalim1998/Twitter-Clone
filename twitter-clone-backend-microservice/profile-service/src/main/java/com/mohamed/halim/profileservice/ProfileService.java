package com.mohamed.halim.profileservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.mohamed.halim.profileservice.config.JwtService;
import com.mohamed.halim.profileservice.model.AuthResponse;
import com.mohamed.halim.profileservice.model.Profile;
import com.mohamed.halim.profileservice.model.RegisterDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ProfileService {
    private ProfileRepository profileRepository;
    private JwtService jwtService;

    @RabbitListener(queues = "register-user")
    public AuthResponse registerUser(RegisterDto dto) {
        if (profileRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email used before");
        }
        if (profileRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("username used before");
        }
        log.error(dto.toString());
        Profile saved = profileRepository.save(dto.toProfile());
        return buildAuthResponse(saved);
    }

    private AuthResponse buildAuthResponse(Profile profile) {
        return AuthResponse.builder()
                .username(profile.getUsername())
                .email(profile.getEmail())
                .token(jwtService.generateToken(profile))
                .build();
    }

}

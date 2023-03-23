package com.mohamed.halim.twitterclone.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mohamed.halim.twitterclone.model.Profile;
import com.mohamed.halim.twitterclone.model.dto.AuthResponse;
import com.mohamed.halim.twitterclone.model.dto.UserDto;
import com.mohamed.halim.twitterclone.repository.ProfileRepository;
import com.mohamed.halim.twitterclone.security.JwtService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileService {
    private ProfileRepository profileRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public AuthResponse registerUser(UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Profile saved = profileRepository.save(dto.toProfile());

        return AuthResponse.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .token(jwtService.generateToken(saved))
                .build();
    }

}

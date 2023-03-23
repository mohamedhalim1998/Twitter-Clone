package com.mohamed.halim.twitterclone.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohamed.halim.twitterclone.model.dto.AuthResponse;
import com.mohamed.halim.twitterclone.model.dto.UserDto;
import com.mohamed.halim.twitterclone.service.ProfileService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final ProfileService profileService;

    @PostMapping("/register")
    public AuthResponse registerUser(@RequestBody UserDto dto) {
        return profileService.registerUser(dto);
    }

}

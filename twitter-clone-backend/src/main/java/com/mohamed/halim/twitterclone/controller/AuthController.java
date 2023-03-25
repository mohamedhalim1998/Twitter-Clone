package com.mohamed.halim.twitterclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.halim.twitterclone.model.dto.AuthResponse;
import com.mohamed.halim.twitterclone.model.dto.RegisterDto;
import com.mohamed.halim.twitterclone.service.ProfileService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final ProfileService profileService;

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthResponse registerUser(@RequestBody RegisterDto dto) {
        return profileService.registerUser(dto);
    }

    @PostMapping("/verify_token")
    public void verifyToken(@RequestBody String json) throws JsonMappingException, JsonProcessingException {
        JsonNode parent = new ObjectMapper().readTree(json);
        String token = parent.get("token").asText();
        profileService.verifyToken(token);

    }

}

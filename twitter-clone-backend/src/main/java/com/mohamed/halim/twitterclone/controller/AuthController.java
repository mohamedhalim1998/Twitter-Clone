package com.mohamed.halim.twitterclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import com.mohamed.halim.twitterclone.model.dto.LoginDto;
import com.mohamed.halim.twitterclone.model.dto.RegisterDto;
import com.mohamed.halim.twitterclone.service.ProfileService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
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

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginDto loginDto) {
        return profileService.login(loginDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        log.error("LOGOUT: ");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, null, auth);
        }
        return ResponseEntity.ok("Logged out successfully");
    }

}

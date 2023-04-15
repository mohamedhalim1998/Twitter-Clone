package com.mohamed.halim.authservice;


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
import com.mohamed.halim.authservice.model.AuthResponse;
import com.mohamed.halim.authservice.model.LoginDto;
import com.mohamed.halim.authservice.model.RegisterDto;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthResponse registerUser(@RequestBody RegisterDto dto)  {
        return authService.registerUser(dto);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }
    @PostMapping("/verify_token")
    public void verifyToken(@RequestBody String json) throws JsonMappingException, JsonProcessingException {
        JsonNode parent = new ObjectMapper().readTree(json);
        String token = parent.get("token").asText();
        authService.verifyToken(token);

    }

}

package com.mohamed.halim.profileservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mohamed.halim.profileservice.model.ProfileDto;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProfileController {
    private ProfileService profileService;

    @GetMapping("/{username}")
    public ProfileDto getProfile(@PathVariable String username) {
        return profileService.getProfile(username);
    }
}

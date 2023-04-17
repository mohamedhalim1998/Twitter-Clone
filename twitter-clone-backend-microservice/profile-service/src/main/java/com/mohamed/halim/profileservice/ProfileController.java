package com.mohamed.halim.profileservice;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PostMapping("/follow/{username}")
    public void followUser(@PathVariable String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        profileService.follow(auth, username);
    }

    @PostMapping("/unfollow/{username}")
    public void unfollowUser(@PathVariable String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        profileService.unfollow(auth, username);
    }

}

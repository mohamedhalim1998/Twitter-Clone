package com.mohamed.halim.twitterclone.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohamed.halim.twitterclone.model.dto.ProfileDto;
import com.mohamed.halim.twitterclone.service.ProfileService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final ProfileService profileService;

    @PostMapping("/block/{username}")
    public void blockUser(Principal principal, @PathVariable String username) {
        profileService.block(principal.getName(), username);
    }

    @PostMapping("/follow/{username}")
    public void followUser(Principal principal, @PathVariable String username) {
        profileService.follow(principal.getName(), username);
    }   
    @PostMapping("/unfollow/{username}")
    public void unfollowUser(Principal principal, @PathVariable String username) {
        profileService.unfollow(principal.getName(), username);
    }

    @GetMapping("/{username}")
    public ProfileDto getProfile(@PathVariable String username) {
        return profileService.getProfile(username);
    }
}

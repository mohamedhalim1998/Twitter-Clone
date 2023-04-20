package com.mohamed.halim.profileservice;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mohamed.halim.dtos.ProfileDto;
import com.mohamed.halim.dtos.ProfileInfo;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class ProfileController {
    private ProfileService profileService;

    @GetMapping("/{username}")
    public ProfileDto getProfile(@PathVariable String username) {
        return profileService.getProfile(username);
    }
 @GetMapping("/logged")
    public ProfileDto getCurrProfile( @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        return profileService.getCurrProfile(auth);
    }

    @PostMapping("/follow/{username}")
    public void followUser(@PathVariable String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        profileService.follow(auth, username);
    }

    @PostMapping("/unfollow/{username}")
    public void unfollowUser(@PathVariable String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        profileService.unfollow(auth, username);
    }
    @PostMapping("/block/{username}")
    public void blockUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @PathVariable String username) {
        profileService.block(auth, username);
    }
    @PatchMapping
    public ProfileDto updateProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestPart("profile_info") ProfileInfo profileInfo,
            @RequestPart(name = "profile_img", required = false) MultipartFile profileImage,
            @RequestPart(name = "cover_img", required = false) MultipartFile coverImage) throws IllegalStateException, IOException {
        return profileService.updateProfile(auth, profileInfo, profileImage, coverImage);
    }

}

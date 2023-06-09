package com.mohamed.halim.twitterclone.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mohamed.halim.twitterclone.model.Block;
import com.mohamed.halim.twitterclone.model.Profile;
import com.mohamed.halim.twitterclone.model.ProfileInfo;
import com.mohamed.halim.twitterclone.model.dto.AuthResponse;
import com.mohamed.halim.twitterclone.model.dto.LoginDto;
import com.mohamed.halim.twitterclone.model.dto.ProfileDto;
import com.mohamed.halim.twitterclone.model.dto.RegisterDto;
import com.mohamed.halim.twitterclone.model.dto.TweetDto;
import com.mohamed.halim.twitterclone.repository.BlockRepository;
import com.mohamed.halim.twitterclone.repository.ProfileRepository;
import com.mohamed.halim.twitterclone.repository.TweetRepository;
import com.mohamed.halim.twitterclone.security.JwtService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileService {
    private ProfileRepository profileRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private FollowService followService;
    private TweetRepository tweetRepository;
    private BlockRepository blockRepository;
    private MediaService mediaService;

    public AuthResponse registerUser(RegisterDto dto) {
        if (profileRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email used before");
        }
        if (profileRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("username used before");
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Profile saved = profileRepository.save(dto.toProfile());
        return buildAuthResponse(saved);
    }

    public void verifyToken(String token) {
        profileRepository.findByUsername(jwtService.extractUsername(token)).ifPresentOrElse((profile -> {
            if (!jwtService.isTokenValid(token, profile)) {
                throw new RuntimeException("Token is Invalid");
            }
        }), () -> {
            throw new RuntimeException("Token is Invalid");
        });
    }

    public AuthResponse login(LoginDto dto) {
        var profileOptional = profileRepository.findByUsername(dto.getUsername());
        if (profileOptional.isEmpty()) {
            profileOptional = profileRepository.findByEmail(dto.getUsername());
        }
        if (profileOptional.isPresent()
                && passwordEncoder.matches(dto.getPassword(), profileOptional.get().getPassword())) {

            return buildAuthResponse(profileOptional.get());
        }
        throw new RuntimeException("Username or Password is incorrect");

    }

    private AuthResponse buildAuthResponse(Profile profile) {
        return AuthResponse.builder()
                .username(profile.getUsername())
                .email(profile.getEmail())
                .token(jwtService.generateToken(profile))
                .build();
    }

    public List<ProfileDto> searchProfiles(String query) {
        return profileRepository.searchProfiles(query, PageRequest.of(0, 20)).stream().map(this::mapToDto).toList();
    }

    private ProfileDto mapToDto(Profile profile) {
        ProfileDto dto = ProfileDto.fromProfile(profile);
        dto.setFollowing(followService.countUserFollowing(profile.getUsername()));
        dto.setFollowers(followService.countUserFollower(profile.getUsername()));
        dto.setTweets(tweetRepository.countByAuthorId(profile.getUsername()));
        return dto;

    }

    public void block(String blocker, String blocking) {
        blockRepository.save(Block.builder().blocker(blocker).blocking(blocking).build());
    }

    public void follow(String follower, String following) {
        followService.addFollow(follower, following);
    }

    public void unfollow(String follower, String following) {
        followService.removeFollow(follower, following);

    }

    public ProfileDto getProfile(String username) {
        return profileRepository.findByUsername(username).map(this::mapToDto).get();
    }

    public ProfileDto updateProfile(String username, ProfileInfo profileInfo, MultipartFile profileImage,
            MultipartFile coverImage) throws IllegalStateException, IOException {
        Profile profile = profileRepository.findById(username).get();
        profile.setFullname(profileInfo.getName());
        profile.setBio(profileInfo.getBio());
        profile.setLocation(profileInfo.getLocation());
        if (profileImage != null) {
            profile.setProfileImageUrl(mediaService.convertToUrl(mediaService.saveMediaFile(profileImage)));
        }
        if (coverImage != null) {
            profile.setCoverImageUrl(mediaService.convertToUrl(mediaService.saveMediaFile(coverImage)));
        }
        return mapToDto(profileRepository.save(profile));

    }

    public List<TweetDto> getUserTweets(String name) {
        return null;
    }

}

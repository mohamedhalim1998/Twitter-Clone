package com.mohamed.halim.twitterclone.model.dto;

import com.mohamed.halim.twitterclone.model.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProfileDto {
    private String username;
    private String email;
    private String password;
    private long birthDay;
    private long createdAt;
    private String bio;
    private String location;
    private Long pinnedTweetId;
    private String profileImageUrl;
    private String coverImageUrl;
    private boolean profileProtected;
    private int folowers;
    private int following;
    private int tweets;

    public static ProfileDto fronProfile(Profile profile) {
        return ProfileDto.builder()
                .username(profile.getUsername())
                .email(profile.getEmail())
                .birthDay(profile.getBirthDay().getTime())
                .createdAt(profile.getCreatedAt().getTime())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .pinnedTweetId(profile.getPinnedTweetId())
                .profileImageUrl(profile.getProfileImageUrl())
                .coverImageUrl(profile.getCoverImageUrl())
                .profileProtected(profile.isProfileProtected())
                .build();
    }

}

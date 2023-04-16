package com.mohamed.halim.profileservice.model;

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
    private String fullname;
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
    private int followers;
    private int following;
    private int tweets;

    public static ProfileDto fromProfile(Profile profile) {
        return ProfileDto.builder()
                .fullname(profile.getFullname())
                .username(profile.getUsername())
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

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
public class UserDto {
    private String username;
    private String email;
    private String password;

    public Profile toProfile() {
        return Profile.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }

}

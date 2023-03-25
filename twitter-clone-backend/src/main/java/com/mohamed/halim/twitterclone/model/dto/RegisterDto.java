package com.mohamed.halim.twitterclone.model.dto;

import java.util.Date;

import com.mohamed.halim.twitterclone.model.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegisterDto {
    private String username;
    private String email;
    private String password;
    private long birthDay;

    public Profile toProfile() {
        return Profile.builder()
                .username(username)
                .email(email)
                .password(password)
                .birthDay(new Date(birthDay))
                .build();
    }

}

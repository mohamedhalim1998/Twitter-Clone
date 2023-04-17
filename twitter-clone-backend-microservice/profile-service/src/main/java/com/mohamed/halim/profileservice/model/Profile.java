package com.mohamed.halim.profileservice.model;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@Entity
public class Profile {
    @Id
    private String username;
    private String fullname;
    private String email;
    private String password;
    private Date birthDay;
    private Date createdAt;
    private String bio;
    private String location;
    private Long pinnedTweetId;
    private String profileImageUrl;
    private String coverImageUrl;
    private boolean profileProtected;

}

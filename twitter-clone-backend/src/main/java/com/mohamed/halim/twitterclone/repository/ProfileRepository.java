package com.mohamed.halim.twitterclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    Profile findByUsername(String username);
}

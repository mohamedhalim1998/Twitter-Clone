package com.mohamed.halim.twitterclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohamed.halim.twitterclone.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByUsername(String username);
}

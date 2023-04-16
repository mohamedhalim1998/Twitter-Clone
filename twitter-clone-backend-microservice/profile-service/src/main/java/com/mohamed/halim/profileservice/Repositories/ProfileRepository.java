package com.mohamed.halim.profileservice.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mohamed.halim.profileservice.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByUsername(String username);

    Optional<Profile> findByEmail(String username);

    @Query("SELECT p FROM Profile p WHERE p.username LIKE CONCAT('%', :query, '%') OR p.fullname LIKE CONCAT('%', :query, '%')")
    List<Profile> searchProfiles(String query, PageRequest of);
}

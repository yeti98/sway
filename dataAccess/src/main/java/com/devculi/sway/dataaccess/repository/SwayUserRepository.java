package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.SwayUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwayUserRepository extends JpaRepository<SwayUser, Long> {
    Optional<SwayUser> getByUsername(String username);
}

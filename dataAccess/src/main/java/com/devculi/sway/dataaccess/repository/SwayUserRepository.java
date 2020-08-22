package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.SwayUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SwayUserRepository extends JpaRepository<SwayUser, Long> {
  Optional<SwayUser> getByUsername(String username);

  SwayUser findByUsername(String username);

  @Query("SELECT u FROM SwayUser u WHERE u.username LIKE :keyword OR u.name like :keyword")
  List<SwayUser> findByUsernameOrNameLike(@Param("keyword") String keyword);
}

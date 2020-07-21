package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.SwayTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwayTestRepository extends JpaRepository<SwayTest, Long> {
}

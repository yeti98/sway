package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwayTestRepository extends JpaRepository<SwayTest, Long> {
  Page<SwayTest> findByTestTypeAndActive(TestType type, boolean active, Pageable pageable);
}

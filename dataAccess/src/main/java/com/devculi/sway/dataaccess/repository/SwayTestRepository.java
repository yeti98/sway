package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.Subject;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SwayTestRepository extends JpaRepository<SwayTest, Long> {
  Page<SwayTest> findByTestTypeAndActive(TestType type, boolean active, Pageable pageable);

  @Query("SELECT st FROM SwayTest st WHERE st.testId LIKE :keyword AND st.testType = :type")
  List<SwayTest> findByTestIdLikeAndTypeEqual(
      @Param("keyword") String keyword, @Param("type") TestType type);

  Optional<SwayTest> findByActiveAndSlug(boolean status, String slug);

  List<SwayTest> findAllByTestTypeAndSubject(TestType testType, Subject subject);
}

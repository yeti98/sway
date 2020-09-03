package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.SwayClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SwayClassRepository extends JpaRepository<SwayClass, Long> {
  @Query("SELECT cl FROM SwayClass cl WHERE cl.classId LIKE :keyword")
  List<SwayClass> findByClassIdLike(@Param("keyword") String keyword);

  Optional<SwayClass> findByActiveAndSlug(boolean status, String slug);

  Page<SwayClass> findByActive(boolean isActive, Pageable pageable);
}

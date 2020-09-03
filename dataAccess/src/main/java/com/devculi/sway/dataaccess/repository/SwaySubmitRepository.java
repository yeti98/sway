package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface SwaySubmitRepository extends JpaRepository<SwaySubmit, Long> {
  SwaySubmit getFirstBySwayClassAndSwayUserOrderByCreatedAtDesc(
      SwayClass swayClass, SwayUser swayUser);

  SwaySubmit findFirstBySwayUserAndSwayClassAndLessonAndSwayTestAndIsPassed(
      SwayUser swayUser, SwayClass swayClass, Lesson lesson, SwayTest test, boolean isPassed);
}

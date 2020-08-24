package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
  @Query("SELECT c FROM Course c WHERE c.courseId LIKE :keyword")
  List<Course> findByCourseIdLike(@Param("keyword") String keyword);
}

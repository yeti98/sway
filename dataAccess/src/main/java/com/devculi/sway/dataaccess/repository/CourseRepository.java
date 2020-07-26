package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}

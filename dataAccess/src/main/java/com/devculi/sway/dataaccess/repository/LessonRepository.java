package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
  @Query("SELECT ls FROM Lesson ls WHERE ls.lessonId LIKE :keyword")
  List<Lesson> findByLessonIdLike(@Param("keyword") String keyword);
}

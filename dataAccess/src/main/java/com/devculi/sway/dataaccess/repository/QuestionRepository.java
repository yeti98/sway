package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  @Query("SELECT q FROM Question q WHERE lower(q.questionId) LIKE :keyword")
  List<Question> findByQuestionIdLike(@Param("keyword") String keyword);
}

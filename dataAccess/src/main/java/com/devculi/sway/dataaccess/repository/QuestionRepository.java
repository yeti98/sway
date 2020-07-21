package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {}

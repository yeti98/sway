package com.devculi.sway.business.shared.model;

import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionModelWrapper {
  List<QuestionModel2> questions;

  public QuestionModelWrapper(List<QuestionModel2> questions) {
    this.questions = questions;
  }

  public QuestionModelWrapper() {}

  public List<QuestionModel2> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = new ArrayList<>();
    for (Question q : questions) {
      this.questions.add(Entity2DTO.toQuestionModel2(q));
    }
  }
}

package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.*;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.dataaccess.repository.SwaySubmitRepository;
import com.devculi.sway.manager.service.interfaces.ISubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwaySubmitService implements ISubmitService {
  @Autowired SwaySubmitRepository submitRepository;

  @Override
  public boolean isSubmitPassed(
      SwayUser swayUser, SwayClass swayClass, Lesson lesson, SwayTest test) {
    SwaySubmit submit =
        submitRepository.findFirstBySwayUserAndSwayClassAndLessonAndSwayTestAndIsPassed(
            swayUser, swayClass, lesson, test, true);
    return submit != null;
  }

  @Override
  public SwaySubmit saveSubmittedHomework(
      SwayUser currentUser,
      SwayClass currentClass,
      Lesson currentLesson,
      SwayTest currentTest,
      Integer numberOfCorrectAns) {
    SwaySubmit submit = new SwaySubmit();
    // score
    int numberOfQuestion = currentTest.getQuestions().size();
    double score = (double) numberOfCorrectAns * 10 / numberOfQuestion;
    score = (double) Math.round(score * 100) / 100;
    submit.setScore(score);
    submit.setScoreInString(String.format("%s / %s", numberOfCorrectAns, numberOfQuestion));
    // identifier
    submit.setSwayTest(currentTest);
    submit.setSwayClass(currentClass);
    submit.setLesson(currentLesson);
    submit.setSwayUser(currentUser);
    // type of submit
    submit.setSubmitType(TestType.HOMEWORK);
    // passed or not
    submit.setPassed(currentClass.getMinScore() < score);
    submitRepository.save(submit);
    return submit;
  }
}

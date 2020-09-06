package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISubmitService {
  boolean isSubmitPassed(SwayUser swayUser, SwayClass swayClass, Lesson lesson, SwayTest test);

  SwaySubmit saveSubmittedHomework(
      SwayUser currentUser,
      SwayClass currentClass,
      Lesson currentLesson,
      SwayTest currentTest,
      Integer numberOfCorrectAns);

  List<SwaySubmit> getAllSubmitsOfTest(SwayTest swayTest, SwayClass swayClass);
}

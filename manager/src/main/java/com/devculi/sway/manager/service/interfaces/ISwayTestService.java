package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.request.UpsertTestRequest;
import com.devculi.sway.dataaccess.entity.*;
import com.devculi.sway.dataaccess.entity.enums.Subject;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISwayTestService {
  SwayTest getTestByID(Long id);

  SwayTest getTestBySlug(String slug);

  SwayTest deleteTestByID(Long id);

  Page<SwayTest> getHomeworkByPage(Integer page);

  SwayTest createTestByType(TestType testType);

  SwayTest updateHomeWork(Long id, UpsertTestRequest updateHomeworkRequest);

  SwayTest insertQuestions(Long targetID, List<Question> questions);

  List<SwayTest> searchBy(String keyword, String testType, boolean isIgnoreCase);

  Page<SwayTest> getTestOnlineByPage(Integer page);

  boolean isPassedTest(
      final SwayUser swayUser, final SwayClass swayClass, final Lesson lesson, final SwayTest test);

  Integer countCorrectAnswer(final SwayTestModel swayTestModel, final SwayTest swayTest)
      throws Exception;

  List<SwayTest> getTestOnlineBySubject(Subject subject);


}

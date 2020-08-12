package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.business.shared.model.QuestionModel;
import com.devculi.sway.business.shared.request.UpsertTestRequest;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.dataaccess.repository.SwayTestRepository;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SwayTestService implements ISwayTestService {
  @Autowired IQuestionService questionService;
  @Autowired SwayTestRepository testRepository;

  @Value("${site.admin.pagination.limit.test}")
  private Integer TestPerPage;

  @Override
  public SwayTest getTestByID(Long id) {
    Optional<SwayTest> byId = testRepository.findById(id);
    return byId.orElseThrow(
        () -> new RecordNotFoundException(SwayTest.class, "id", id.toString()));
  }

  @Override
  public SwayTest deleteTestByID(Long id) {
    SwayTest testByID = getTestByID(id);
    testRepository.delete(testByID);
    return testByID;
  }

  @Override
  public Page<SwayTest> getHomeworkByPage(Integer page) {
    Pageable pageable = PageRequest.of(page, TestPerPage, Sort.by("createdAt").descending());
    return testRepository.findByTestTypeAndActive(TestType.HOMEWORK, true, pageable);
  }

  @Override
  public SwayTest createTestByType(TestType testType) {
    SwayTest swayTest = new SwayTest();
    swayTest.setTestType(testType);
    swayTest.setSubmits(new ArrayList<>());
    swayTest.setQuestions(new ArrayList<>());
    swayTest.setActive(true);
    swayTest.setDeadline(null);
    testRepository.save(swayTest);
    return swayTest;
  }

  @Override
  public SwayTest updateHomeWork(Long id, UpsertTestRequest updateHomeworkRequest) {
    SwayTest swayTest = getTestByID(id);
    Set<String> nullProperties = PropertyUtils.getNullProperties(updateHomeworkRequest);
    String testName = updateHomeworkRequest.getTestName();
    String testId = updateHomeworkRequest.getTestId();
    swayTest.setTestId(testId);
    if (!nullProperties.contains(testName)) {
      swayTest.setTestName(testName);
    }
    swayTest.setTestType(TestType.HOMEWORK);
    Collection<Question> questions = new ArrayList<>();
    List<Long> questionIdList =
        updateHomeworkRequest.getQuestions().stream()
            .map(QuestionModel::getId)
            .collect(Collectors.toList());
    questionIdList.forEach(
        qID -> {
          Question questionByID = questionService.getQuestionByID(qID);
          if (!questions.contains(questionByID)) {
            questions.add(questionByID);
          }
        });
    swayTest.setQuestions(questions);
    testRepository.save(swayTest);
    return swayTest;
  }

  @Override
  public SwayTest insertQuestions(Long targetID, List<Question> questions) {
    SwayTest testByID = getTestByID(targetID);
    questions.forEach(question -> {
      testByID.getQuestions().add(question);
    });
    testRepository.save(testByID);
    return testByID;
  }
}

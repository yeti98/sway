package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.business.shared.model.QuestionModel;
import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.request.UpsertTestRequest;
import com.devculi.sway.dataaccess.entity.*;
import com.devculi.sway.dataaccess.entity.enums.Subject;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.dataaccess.repository.SwayTestRepository;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.utils.PropertyUtils;
import com.devculi.sway.utils.StringUtils;
import org.springframework.beans.BeanUtils;
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
  @Autowired SwaySubmitService swaySubmitService;

  @Value("${site.admin.pagination.limit.test}")
  private Integer TestPerPage;

  @Override
  public SwayTest getTestByID(Long id) {
    Optional<SwayTest> byId = testRepository.findById(id);
    return byId.orElseThrow(() -> new RecordNotFoundException(SwayTest.class, "id", id.toString()));
  }

  @Override
  public SwayTest getTestBySlug(String slug) {
    Optional<SwayTest> bySlug = testRepository.findByActiveAndSlug(true, slug);
    return bySlug.orElseThrow(() -> new RecordNotFoundException(SwayTest.class, "slug", slug));
  }

  @Override
  public List<SwayTest> getTestOnlineBySubject(Subject subject) {
    List<SwayTest> bySubject =
        testRepository.findAllByTestTypeAndSubject(TestType.TEST_ONLINE, subject);
    return bySubject;
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
  public Page<SwayTest> getTestOnlineByPage(Integer page) {
    Pageable pageable = PageRequest.of(page, TestPerPage, Sort.by("createdAt").descending());
    return testRepository.findByTestTypeAndActive(TestType.TEST_ONLINE, true, pageable);
  }

  @Override
  public boolean isPassedTest(
      SwayUser swayUser, SwayClass swayClass, Lesson lesson, SwayTest test) {
    return swaySubmitService.isSubmitPassed(swayUser, swayClass, lesson, test);
  }

  @Override
  public Integer countCorrectAnswer(SwayTestModel submittedTestModel, SwayTest testByID) {
    int count = 0;
    for (QuestionModel question : submittedTestModel.getQuestions()) {
      Long questionId = question.getId();
      String selected = question.getSelected();
      Question questionEntity = questionService.getQuestionByID(questionId);
      boolean isCorrect = questionEntity.getAnswer().equalsIgnoreCase(selected);
      BeanUtils.copyProperties(questionEntity, question);
      if (isCorrect) {
        count += 1;
        question.setWrong(false); // used later when render result view
      } else {
        question.setWrong(true); // used later when render result view
      }
    }
    return count;
  }

  @Override
  public SwayTest createTestByType(TestType testType) {
    SwayTest swayTest = new SwayTest();
    swayTest.setTestType(testType);
    swayTest.setSubject(Subject.ENGLISH);
    swayTest.setQuestions(new ArrayList<>());
    swayTest.setActive(false);
    swayTest.setDeadline(null);
    swayTest.setSlug(null);
    testRepository.save(swayTest);
    return swayTest;
  }

  @Override
  public SwayTest updateHomeWork(Long id, UpsertTestRequest updateHomeworkRequest) {
    SwayTest swayTest = getTestByID(id);
    Set<String> nullProperties = PropertyUtils.getNullProperties(updateHomeworkRequest);
    String testName = updateHomeworkRequest.getTestName();
    String testId = updateHomeworkRequest.getTestId();
    if (!nullProperties.contains("testName")) {
      swayTest.setTestName(testName);
      // first time update
      if (!swayTest.isActive()) {
        swayTest.setActive(true);
      }
      if (StringUtils.isNullOrEmpty(swayTest.getSlug())) {
        swayTest.setSlug(StringUtils.makeSlug(testName, swayTest.getId().toString(), false));
      }
    }
    if (!nullProperties.contains("testId")) {
      swayTest.setTestId(testId);
    }
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
    swayTest.setSubject(updateHomeworkRequest.getSubject());
    testRepository.save(swayTest);
    return swayTest;
  }

  @Override
  public SwayTest insertQuestions(Long targetID, List<Question> questions) {
    SwayTest testByID = getTestByID(targetID);
    questions.forEach(
        question -> {
          testByID.getQuestions().add(question);
        });
    testRepository.save(testByID);
    return testByID;
  }

  @Override
  public List<SwayTest> searchBy(String keyword, String testType, boolean isIgnoreCase) {
    if (isIgnoreCase) {
      keyword = "%" + keyword.toLowerCase() + "%";
    } else {
      keyword = "%" + keyword + "%";
    }
    TestType type = TestType.TEST_ONLINE;
    if (testType.equalsIgnoreCase(TestType.HOMEWORK.toString())) {
      type = TestType.HOMEWORK;
    }
    return testRepository.findByTestIdLikeAndTypeEqual(keyword, type);
  }
}

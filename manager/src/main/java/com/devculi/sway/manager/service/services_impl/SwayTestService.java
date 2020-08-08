package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.dataaccess.repository.SwayTestRepository;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SwayTestService implements ISwayTestService {
  @Autowired SwayTestRepository testRepository;
  @Value("${site.admin.pagination.limit.test}")
  private Integer TestPerPage;

  @Override
  public SwayTest getTestByID(Long id) {
    Optional<SwayTest> byId = testRepository.findById(id);
    return byId.orElse(null);
  }

  @Override
  public SwayTest deleteTestByID(Long id) {
    return null;
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
}

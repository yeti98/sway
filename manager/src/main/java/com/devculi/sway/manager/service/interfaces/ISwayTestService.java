package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.request.UpsertTestRequest;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISwayTestService {
  SwayTest getTestByID(Long id);

  SwayTest deleteTestByID(Long id);

  Page<SwayTest> getHomeworkByPage(Integer page);

  SwayTest createTestByType(TestType testType);

  SwayTest updateHomeWork(Long id, UpsertTestRequest updateHomeworkRequest);

  SwayTest insertQuestions(Long targetID, List<Question> questions);
}

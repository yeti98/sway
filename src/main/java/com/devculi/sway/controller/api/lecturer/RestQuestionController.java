package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class RestQuestionController {
  @Autowired IQuestionService questionService;

  @Transactional(rollbackFor = Exception.class)
  public Question insertQuestion(UpsertQuestionRequest insertQuestionRequest) {
    return questionService.insertQuestion(insertQuestionRequest);
  }

  @Transactional(rollbackFor = Exception.class)
  public Question updateQuestion(long id, UpsertQuestionRequest updateQuestionRequest) {
    return questionService.updateQuestion(id, updateQuestionRequest);
  }

  @Transactional(rollbackFor = Exception.class)
  public Long deleteQuestion(Long id) {
    return questionService.deleteQuestion(id);
  }
}

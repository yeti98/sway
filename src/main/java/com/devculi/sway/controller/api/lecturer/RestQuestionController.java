package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.business.shared.model.QuestionModel;
import com.devculi.sway.business.shared.model.QuestionModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
@RequestMapping("/api/questions")
public class RestQuestionController {
  @Autowired IQuestionService questionService;

  @PostMapping
  @Transactional(rollbackFor = Exception.class)
  public QuestionModel insertQuestion(@RequestBody UpsertQuestionRequest insertQuestionRequest) {
    Question question = questionService.insertQuestion(insertQuestionRequest);
    return Entity2DTO.question2DTO(question);
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

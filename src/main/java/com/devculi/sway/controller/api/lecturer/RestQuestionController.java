package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.business.shared.model.QuestionModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.BaseController;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
public class RestQuestionController extends BaseController {
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

  @GetMapping("/search")
  public ResponseEntity<Object> searchByKeyword(
          @RequestParam(name = "query", defaultValue = "") String keyword) {
    if (keyword.length() == 0) {
      return ok(new ArrayList<>());
    }
    List<Question> results = questionService.searchBy(keyword, true);
    return ok(results.stream().map(Entity2DTO::question2DTO).collect(Collectors.toList()));
  }

}

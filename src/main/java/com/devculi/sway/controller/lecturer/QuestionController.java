package com.devculi.sway.controller.lecturer;

import com.devculi.sway.business.shared.factory.SwayFactory;
import com.devculi.sway.controller.BaseController;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

@RestController
@RequestMapping("/questions")
public class QuestionController extends BaseController {
  @Autowired IQuestionService questionService;


  @PostMapping("/{id}")
  ResponseEntity getQuestionByID(@PathVariable(name = "id") Long questionID) {
    return ok(questionService.getQuestionByID(questionID));
  }

  @PostMapping
  ResponseEntity insertQuestion(@RequestBody UpsertQuestionRequest insertQuestionRequest) {
    SwayFactory.getQuestionValidator().validateUpsertQuestion(insertQuestionRequest);
    return ok(questionService.insertQuestion(insertQuestionRequest));
  }

  @PutMapping("/{id}")
  ResponseEntity updateQuestionInClass(
      @PathVariable(name = "id") Long questionID,
      @RequestBody UpsertQuestionRequest updateQuestionRequest) {
    return ok(questionService.updateQuestion(questionID, updateQuestionRequest));
  }

  @DeleteMapping("/{id}")
  ResponseEntity deleteQuestion(@PathVariable(name = "id") Long questionID) {
    return ok(questionService.deleteQuestion(questionID));
  }
}

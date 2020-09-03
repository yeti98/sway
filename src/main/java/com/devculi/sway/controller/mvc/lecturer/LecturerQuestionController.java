package com.devculi.sway.controller.mvc.lecturer;

import com.devculi.sway.business.shared.model.QuestionModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.lecturer.LecRestQuestionController;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.interceptor.attr.annotations.ManageQuestionsPage;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@Controller
@RequestMapping("/admin/manage/questions")
@ManageQuestionsPage
public class LecturerQuestionController {
  private final LinkedList<QuestionModel> recentAddedQuestion = new LinkedList<>();
  @Autowired LecRestQuestionController questionController;

  @GetMapping
  public String renderQuestionsView(Model model) {

    model.addAttribute("questions", recentAddedQuestion);
    return "admin/question/index";
  }

  @PostMapping
  @Transactional(rollbackFor = Exception.class)
  public String insertQuestion(
      Model model, @RequestBody UpsertQuestionRequest insertQuestionRequest) {
    QuestionModel questionModel = questionController.insertQuestion(insertQuestionRequest);
    recentAddedQuestion.addFirst(questionModel);
    model.addAttribute("questions", recentAddedQuestion);
    return "admin/question/index :: questionTableBody";
  }

  @PutMapping("/{id}")
  @Transactional(rollbackFor = Exception.class)
  public String updateQuestion(
      Model model,
      @PathVariable(name = "id") Long id,
      @RequestBody UpsertQuestionRequest updateQuestionRequest)
      throws Exception {
    //    System.out.println("INDEX", recentAddedQuestion.indexOf(question));
    if (!id.equals(updateQuestionRequest.getId())) {
      throw new Exception("Question Id must be exact");
    }
    Question question = questionController.updateQuestion(id, updateQuestionRequest);
    int beRemovedIndex = getModelIndex(id);
    recentAddedQuestion.remove(beRemovedIndex);
    recentAddedQuestion.add(beRemovedIndex, Entity2DTO.question2DTO(question));
    model.addAttribute("questions", recentAddedQuestion);
    return "admin/question/index :: questionTableBody";
  }

  @DeleteMapping("/{id}")
  @Transactional(rollbackFor = Exception.class)
  public String deleteQuestion(Model model, @PathVariable(name = "id") Long id) {
    questionController.deleteQuestion(id);
    int index = getModelIndex(id);
    recentAddedQuestion.remove(index);
    model.addAttribute("questions", recentAddedQuestion);
    return "admin/question/index :: questionTableBody";
  }

  private int getModelIndex(long id) {
    int index = 0;
    for (QuestionModel questionModel : recentAddedQuestion) {
      if (questionModel.getId().equals(id)) {
        return index;
      }
      index += 1;
    }
    return -1;
  }
}

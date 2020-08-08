package com.devculi.sway.controller.mvc.lecturer;

import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.controller.api.lecturer.RestTestController;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.manager.service.services_impl.SwayTestService;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/manage/homeworks")
public class HomeworkController {
  @Autowired RestTestController testController;
  @Autowired SwayTestService testService;
  //    private LinkedList<QuestionModel> recentAddedQuestion = new LinkedList<>();

  @GetMapping
  public String renderQuestionsView(
      Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    PagingResponse<SwayTestModel> testByPage = testController.getTestByPage(page);
    model.addAttribute("totalPages", testByPage.getContent());
    model.addAttribute("homeworks", testByPage.getContent());
    return "admin/swaytest/homework";
  }

  @GetMapping("/create")
  public String createNewHomework(Model model) {
    SwayTest newSwayTest = testService.createTestByType(TestType.HOMEWORK);
    return "redirect:/admin/manage/homeworks/" + newSwayTest.getId();
  }

  //    @PostMapping
  //    @Transactional(rollbackFor = Exception.class)
  //    public String insertQuestion(
  //            Model model, @RequestBody UpsertQuestionRequest insertQuestionRequest) {
  //        Question question = questionController.insertQuestion(insertQuestionRequest);
  //        recentAddedQuestion.addFirst(Entity2DTO.question2DTO(question));
  //        model.addAttribute("questions", recentAddedQuestion);
  //        return "admin/question/index :: questionTableBody";
  //    }

  //    @PutMapping("/{id}")
  //    @Transactional(rollbackFor = Exception.class)
  //    public String updateQuestion(
  //            Model model,
  //            @PathVariable(name = "id") Long id,
  //            @RequestBody UpsertQuestionRequest updateQuestionRequest)
  //            throws Exception {
  //        //    System.out.println("INDEX", recentAddedQuestion.indexOf(question));
  //        if (!id.equals(updateQuestionRequest.getId())) {
  //            throw new Exception("Question Id must be exact");
  //        }
  //        Question question = questionController.updateQuestion(id, updateQuestionRequest);
  //        int beRemovedIndex = getModelIndex(id);
  //        recentAddedQuestion.remove(beRemovedIndex);
  //        recentAddedQuestion.add(beRemovedIndex, Entity2DTO.question2DTO(question));
  //        model.addAttribute("questions", recentAddedQuestion);
  //        return "admin/question/index :: questionTableBody";
  //    }
  //
  //    @DeleteMapping("/{id}")
  //    @Transactional(rollbackFor = Exception.class)
  //    public String deleteQuestion(Model model, @PathVariable(name = "id") Long id) {
  //        questionController.deleteQuestion(id);
  //        int index = getModelIndex(id);
  //        recentAddedQuestion.remove(index);
  //        model.addAttribute("questions", recentAddedQuestion);
  //        return "admin/question/index :: questionTableBody";
  //    }
  //
  //    private int getModelIndex(long id) {
  //        int index = 0;
  //        for (QuestionModel questionModel : recentAddedQuestion) {
  //            if (questionModel.getId().equals(id)) {
  //                return index;
  //            }
  //            index += 1;
  //        }
  //        return -1;
  //    }

  // HOMEWORK DETAIL
  @GetMapping("/{id}")
  public String create(Model model, @PathVariable(name = "id") Long id) {
    SwayTest newSwayTest = testService.getTestByID(id);
    model.addAttribute("swayTest", newSwayTest);
    return "admin/swaytest/homework/detail";
  }
}

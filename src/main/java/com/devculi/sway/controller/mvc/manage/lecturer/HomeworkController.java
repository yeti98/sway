package com.devculi.sway.controller.mvc.manage.lecturer;

import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
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

  @GetMapping
  public String renderHomeworkView(
      Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    PagingResponse<SwayTestModel> testByPage = testController.getTestByPage(page);
    model.addAttribute("totalPages", testByPage.getTotalPage());
    model.addAttribute("homeworks", testByPage.getContent());
    return "admin/swaytest/homework";
  }

  @GetMapping("/create")
  public String createNewHomework(Model model) {
    SwayTest newSwayTest = testService.createTestByType(TestType.HOMEWORK);
    return "redirect:/admin/manage/homeworks/" + newSwayTest.getId();
  }

  // HOMEWORK DETAIL
  @GetMapping("/{id}")
  public String create(Model model, @PathVariable(name = "id") Long id) {
    SwayTest newSwayTest = testService.getTestByID(id);
    model.addAttribute("swayTest", Entity2DTO.swayTest2DTO(newSwayTest));
    return "admin/swaytest/homework/detail";
  }
}

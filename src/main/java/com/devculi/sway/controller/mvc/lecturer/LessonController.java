package com.devculi.sway.controller.mvc.lecturer;

import com.devculi.sway.business.shared.model.LessonModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.manager.service.interfaces.ILessonService;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/manage/lessons")
public class LessonController {
  @Autowired ILessonService lessonService;

  @GetMapping
  public String renderLessonView(
      Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    Page<Lesson> testByPage = lessonService.getLessonByPage(page);
    PagingResponse<LessonModel> lessonPagingResponse =
        new PagingResponse<>(
            testByPage.getTotalPages(),
            testByPage.getContent().stream()
                .map(Entity2DTO::toLessonModel)
                .collect(Collectors.toList()));
    model.addAttribute("totalPages", lessonPagingResponse.getTotalPage());
    model.addAttribute("lessons", lessonPagingResponse);
    return "admin/lesson/index";
  }


  // HOMEWORK DETAIL
  @GetMapping("/{id}")
  public String create(Model model, @PathVariable(name = "id") Long id) {
//    SwayTest newSwayTest = testService.getTestByID(id);
//    model.addAttribute("swayTest", Entity2DTO.swayTest2DTO(newSwayTest));
    return "/admin/lesson/detail";
  }
}

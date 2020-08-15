package com.devculi.sway.controller.mvc.lecturer;

import com.devculi.sway.business.shared.model.LessonModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.TestType;
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
    PagingResponse<LessonModel> lessonByPage = lessonService.getLessonByPage(page);
    model.addAttribute("totalPages", lessonByPage.getTotalPage());
    model.addAttribute("lessons", lessonByPage.getContent());
    model.addAttribute("current", page);
    return "admin/lesson/index";
  }

  @GetMapping("/create")
  public String createNewLesson(Model model) {
    Lesson newLesson = lessonService.createLesson();
    return "redirect:/admin/manage/lessons/" + newLesson.getId();
  }



  // HOMEWORK DETAIL
  @GetMapping("/{id}")
  public String getLessonDetail(Model model, @PathVariable(name = "id") Long id) {
    Lesson lesson = lessonService.getLessonByID(id);
    model.addAttribute("lesson", Entity2DTO.lesson2Model(lesson));
    return "/admin/lesson/detail";
  }
}

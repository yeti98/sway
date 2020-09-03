package com.devculi.sway.controller.mvc.lecturer;

import com.devculi.sway.business.shared.model.CourseModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Course;
import com.devculi.sway.interceptor.attr.annotations.ManageCoursesPage;
import com.devculi.sway.manager.service.interfaces.ICourseService;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/manage/courses")
@ManageCoursesPage
public class CourseController {
  @Autowired ICourseService courseService;

  @GetMapping
  public String renderCourseView(
      Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    PagingResponse<CourseModel> lessonByPage = courseService.getCourseByPage(page);
    model.addAttribute("totalPages", lessonByPage.getTotalPage());
    model.addAttribute("courses", lessonByPage.getContent());
    model.addAttribute("current", page);
    return "admin/course/index";
  }

  @GetMapping("/create")
  public String createNewCourse(Model model) {
    Course course = courseService.createCourse();
    return "redirect:/admin/manage/courses/" + course.getId();
  }

  // COURSE DETAIL
  @GetMapping("/{id}")
  public String getCourseDetail(Model model, @PathVariable(name = "id") Long id) {
    Course course = courseService.getCourseById(id);
    model.addAttribute("course", Entity2DTO.course2DTO(course));
    return "admin/course/detail";
  }
}

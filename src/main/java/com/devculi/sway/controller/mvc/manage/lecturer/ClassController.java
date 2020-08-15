package com.devculi.sway.controller.mvc.manage.lecturer;

import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Course;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.interceptor.attr.annotations.ManageClassPage;
import com.devculi.sway.manager.service.interfaces.IClassService;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/manage/classes")
@ManageClassPage
public class ClassController {
  @Autowired
  IClassService classService;

  @GetMapping
  public String renderClassView(
          Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    PagingResponse<SwayClassModel> classByPage = classService.getClassByPage(page);
    model.addAttribute("totalPages", classByPage.getTotalPage());
    model.addAttribute("swayClasses", classByPage.getContent());
    model.addAttribute("current", page);
    return "admin/class/index";
  }

  @GetMapping("/create")
  public String createNewCourse(Model model) {
    SwayClass swayClass = classService.createClass();
    return "redirect:/admin/manage/classes/" + swayClass.getId();
  }

  // SWAYCLASS DETAIL
  @GetMapping("/{id}")
  public String getCourseDetail(Model model, @PathVariable(name = "id") Long id) {
    SwayClass swayClass = classService.getClassById(id);
    model.addAttribute("swayClass", Entity2DTO.class2DTO(swayClass));
    return "/admin/class/detail";
  }
}

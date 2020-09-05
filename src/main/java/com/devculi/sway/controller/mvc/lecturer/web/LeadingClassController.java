package com.devculi.sway.controller.mvc.lecturer.web;

import com.devculi.sway.annotations.RequireRoleLecturer;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.manager.service.interfaces.ILecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quan-ly-lop-hoc")
@RequireRoleLecturer
public class LeadingClassController {
  @Autowired ILecturerService lecturerService;

  @GetMapping
  public String renderManageSwayClassView(Model model) {
    List<SwayClass> leadingClasses = lecturerService.getLeadingClasses();
    model.addAttribute(
        "classes", leadingClasses.stream().map(Entity2DTO::class2DTO).collect(Collectors.toList()));
    return "giaovien/quan-ly-lop-hoc/index";
  }
}

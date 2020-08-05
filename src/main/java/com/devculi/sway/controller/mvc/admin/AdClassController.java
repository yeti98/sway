package com.devculi.sway.controller.mvc.admin;

import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.controller.api.admin.AdminController;
import com.devculi.sway.controller.api.lecturer.LecturerController;
import com.devculi.sway.interceptor.attr.annotations.ManageClassPage;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/manage/classes")
@ManageClassPage
public class AdClassController {
  @Autowired
  AdminController adminController;

  @GetMapping
  public String getClasses(
      Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    PagingResponse<SwayClassModel> usersByPage = adminController.getClassByPage(page);
    model.addAttribute("totalPages", usersByPage.getTotalPage());
    model.addAttribute("classes", usersByPage.getContent());
    return "admin/classes";
  }
}

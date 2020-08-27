package com.devculi.sway.controller.mvc.admin;

import com.devculi.sway.controller.api.admin.AdminController;
import com.devculi.sway.interceptor.attr.annotations.ManageUsersPage;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/manage/users")
@ManageUsersPage
public class AdminUserController {
  @Autowired AdminController adminController;

  @GetMapping
  public String getUserByPage(
      @RequestParam(name = "page", defaultValue = "0") Integer page, Model model) {
    PagingResponse<UserModel> usersByPage = adminController.getUsersByPage(page);
    model.addAttribute("totalPages", usersByPage.getTotalPage());
    model.addAttribute("users", usersByPage.getContent());
    model.addAttribute("current", page);
    return "admin/users";
  }
}

package com.devculi.sway.controller.mvc.manage.admin;

import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.admin.AdminController;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/manage/users")
public class AdUserController {
  @Autowired AdminController adminController;
  //  @Autowired IAdminService adminService;

  @GetMapping
  public String getUserByPage(
      @RequestParam(name = "page", defaultValue = "0") Integer page, Model model) {
    PagingResponse<UserModel> usersByPage = adminController.getUsersByPage(page);
    model.addAttribute("totalPages", usersByPage.getTotalPage());
    model.addAttribute("users", usersByPage.getContent());
    return "admin/users";
  }


}

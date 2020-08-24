package com.devculi.sway.controller.mvc.manage.auth;

import com.devculi.sway.config.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthMVCController {
  @Autowired CustomAuthenticationProvider authManager;

  @GetMapping("/login")
  public String renderLoginView(Model model) {
    model.addAttribute("pageTitle", "Đăng nhập");
    return "gdmoi/dang-nhap";
  }

  @GetMapping("/logout")
  public String renderLogoutView(Model model) {
    model.addAttribute("pageTitle", "Đăng xuất");
    return "redirect:/login?logout";
  }
}

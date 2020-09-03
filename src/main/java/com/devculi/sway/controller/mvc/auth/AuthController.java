package com.devculi.sway.controller.mvc.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthController {

  @GetMapping("/login")
  public String dangNhap(Model model) {
    model.addAttribute("pageTitle", "Đăng nhập");
    return "gdmoi/dang-nhap";
  }

  @GetMapping("/dang-nhap")
  public String dangNhapVersion2(Model model) {
    return "redirect:/login";
  }
}

package com.devculi.sway.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthMVCController {

    @GetMapping("/login")
    public String renderLoginView(Model model) {
        model.addAttribute("pageTitle", "Đăng nhập");
        return "login";
    }

    @GetMapping("/logout")
    public String renderLogoutView(Model model) {
        model.addAttribute("pageTitle", "Đăng xuất");
        return "redirect:/login?logout";
    }


}

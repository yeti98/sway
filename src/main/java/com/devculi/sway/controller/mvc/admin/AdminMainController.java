package com.devculi.sway.controller.mvc.admin;

import com.devculi.sway.interceptor.attr.annotations.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController {

    @GetMapping
    public String adminIndex(Model model){
        return "admin/index";
    }
}

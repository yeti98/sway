package com.devculi.sway.controller;

import com.devculi.sway.interceptor.attr.annotations.HomePage;
import com.devculi.sway.manager.service.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
@HomePage
public class MainController {

  @GetMapping
  public String index(Model model) {
    model.addAttribute("activeLink", "index");
    return "index";
  }

  @GetMapping("/gioi-thieu")
  public String gioiThieu(Model model) {
    model.addAttribute("activeLink", "gt");
    model.addAttribute("pageTitle", "Giới thiệu");
    return "gioi-thieu";
  }

  @GetMapping("/doi-ngu")
  public String doiNgu(Model model) {
    model.addAttribute("activeLink", "doi-ngu");
    model.addAttribute("pageTitle", "Đội ngũ");
    return "doi-ngu";
  }

  @GetMapping("/about-us")
  public String aboutUs(Model model) {
    model.addAttribute("activeLink", "about-us");
    model.addAttribute("pageTitle", "About us");
    return "about-us";
  }

  @GetMapping(value = {"/robots.txt", "/robot.txt"})
  @ResponseBody
  public String getRobotsTxt() {
    return "User-agent: *\n" + "Disallow: /admin\n";
  }

  @GetMapping("/homework")
  public String homework(Model model) {
    model.addAttribute("activeLink", "homework");
    model.addAttribute("pageTitle", "Bài tập về nhà");
    return "homework";
  }
}

package com.devculi.sway.controller;

import com.devculi.sway.interceptor.attr.annotations.HomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
@HomePage
public class MainController extends BaseController {
  @Autowired
  HttpServletRequest request;

  @GetMapping
  public String index() {
    return "index";
  }

  @GetMapping(value = {"/robots.txt", "/robot.txt"})
  @ResponseBody
  public String getRobotsTxt() {
    return "User-agent: *\n" + "Disallow: /admin\n";
  }

  @GetMapping("/test")
  public String test() {
    return "bigedu";
  }

  @GetMapping("/login")
  public String renderLoginView(Model model) {
    return "login";
  }
}

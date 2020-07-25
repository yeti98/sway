package com.devculi.sway.interceptor.attr;

import com.devculi.sway.interceptor.attr.annotations.HomePage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@ControllerAdvice(annotations = HomePage.class)
public class HomePageAttrController {

  @Value("${site.page.homepage.title}")
  private String title;

  @ModelAttribute(name = "pageTitle")
  public void getHomePageTitle(Model model) {
    model.addAttribute("pageTitle", title);
  }
}

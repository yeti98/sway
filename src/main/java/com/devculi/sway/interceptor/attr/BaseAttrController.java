package com.devculi.sway.interceptor.attr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@ControllerAdvice
public class BaseAttrController {
  @Value("${site.page.common.title}")
  private String webTitle;

  @ModelAttribute(name = "webTitle")
  public void addPageTitle(Model model) {
    model.addAttribute("webTitle", webTitle);
  }
}

package com.devculi.sway.interceptor.attr.controller;


import com.devculi.sway.interceptor.attr.annotations.ManageLessonsPage;
import com.devculi.sway.interceptor.attr.annotations.ManageUsersPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@ControllerAdvice(annotations = ManageLessonsPage.class)
public class ManageLessonsAttrController {
    @Value("${site.page.manageLesson.title}")
    private String title;

    @ModelAttribute(name = "pageTitle")
    public void getHomePageTitle(Model model) {
        model.addAttribute("pageTitle", title);
    }
}

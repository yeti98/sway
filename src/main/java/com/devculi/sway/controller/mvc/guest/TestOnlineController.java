package com.devculi.sway.controller.mvc.guest;

import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.Subject;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/test-online")
public class TestOnlineController {
  @Autowired ISwayTestService testService;

  @GetMapping
  public String testOnline(Model model, @RequestParam(name = "subject", defaultValue = "ENGLISH") Subject subject) {
    List<SwayTest> englishTestList = testService.getTestOnlineBySubject(Subject.ENGLISH);
    List<SwayTest> koreanTestList = testService.getTestOnlineBySubject(Subject.KOREAN);
    List<SwayTest> chineseTestList = testService.getTestOnlineBySubject(Subject.CHINESE);
    List<SwayTest> japaneseTestList = testService.getTestOnlineBySubject(Subject.JAPANESE);
    System.out.println(englishTestList.size());
    model.addAttribute("pageTitle","Test-online");
    model.addAttribute(
        "englishTests",
        englishTestList.stream().map(Entity2DTO::swayTest2DTO).collect(Collectors.toList()));
    model.addAttribute(
        "koreanTests",
        koreanTestList.stream().map(Entity2DTO::swayTest2DTO).collect(Collectors.toList()));
    model.addAttribute(
        "chineseTests",
        chineseTestList.stream().map(Entity2DTO::swayTest2DTO).collect(Collectors.toList()));
    model.addAttribute(
        "japaneseTests",
        japaneseTestList.stream().map(Entity2DTO::swayTest2DTO).collect(Collectors.toList()));
    return "guest/test-online/index";
  }
}

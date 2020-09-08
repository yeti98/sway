package com.devculi.sway.controller.mvc.guest;

import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.Subject;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/test-online")
public class TestOnlineController {
  private static final String PAGE_TITLE = "Bài test online miễn phí";
  @Autowired ISwayTestService testService;

  @GetMapping
  public String testOnline(Model model) {
    List<SwayTest> englishTestList = testService.getTestOnlineBySubject(Subject.ENGLISH);
    List<SwayTest> koreanTestList = testService.getTestOnlineBySubject(Subject.KOREAN);
    List<SwayTest> chineseTestList = testService.getTestOnlineBySubject(Subject.CHINESE);
    List<SwayTest> japaneseTestList = testService.getTestOnlineBySubject(Subject.JAPANESE);

    model.addAttribute("pageTitle", PAGE_TITLE);
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

  @GetMapping("/{slug}")
  public String getTestBySlug(Model model, @PathVariable(name = "slug") String slug) {
    SwayTest test = testService.getTestBySlug(slug);

    model.addAttribute("pageTitle", PAGE_TITLE);
    model.addAttribute("swayTest", Entity2DTO.swayTest2DTO(test));
    model.addAttribute("slug", slug);

    return "guest/test-online/bai-tap";
  }

  @PostMapping("/nop-bai")
  public String nopBai(
      @ModelAttribute(name = "swayTest") SwayTestModel submittedTestModel,
      Model model,
      @RequestParam(name = "slug") String slug)
      throws Exception {
    model.addAttribute("pageTitle", "Kết quả làm bài test");

    SwayTest currentTest = testService.getTestBySlug(slug);
    int numberOfCorrectAns = testService.countCorrectAnswer(submittedTestModel, currentTest);
    int numberOfQuestion = currentTest.getNumberOfQuestion();

    model.addAttribute("swayTest", submittedTestModel);
    double score = (double) numberOfCorrectAns * 10 / numberOfQuestion;
    score = (double) Math.round(score * 100) / 100;
    model.addAttribute("score", score);
    model.addAttribute(
        "scoreInString", String.format("%s / %s", numberOfCorrectAns, numberOfQuestion));

    return "guest/test-online/ket-qua";
  }
}

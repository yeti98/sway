package com.devculi.sway.controller.mvc;

import com.devculi.sway.business.shared.model.QuestionModel2;
import com.devculi.sway.business.shared.model.QuestionModelWrapper;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.interceptor.attr.annotations.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

  @GetMapping("/loading")
  public String loading(Model model) {
    model.addAttribute("activeLink", "homework");
    model.addAttribute("pageTitle", "Đang tải");
    return "loading";
  }

  @GetMapping("/test-online")
  public String testOnline(Model model) {
    model.addAttribute("activeLink", "test-online");
    model.addAttribute("pageTitle", "Test online");
    List<Question> qs = new ArrayList<>();
    qs.add(
        new Question((long) 1, "A###DEVCULI###B###DEVCULI###C", "A", "ABCD?", "asd", true, "123"));
    qs.add(
        new Question((long) 2, "A###DEVCULI###B###DEVCULI###C", "A", "ABCD?", "asd", true, "124"));
    QuestionModelWrapper wrapper = new QuestionModelWrapper();
    wrapper.setQuestions(qs);
    model.addAttribute("wrapper", wrapper);
    return "test-online";
  }

  @PostMapping("/submit-test")
  public String handleSubmitTest(@ModelAttribute QuestionModelWrapper wrapper, Model model) {
    // Save vao submit

    // Chuyen vao service. Kb o dau
    // Cham diem
    int diem = 0;
    for (QuestionModel2 qm : wrapper.getQuestions()) {
      if (qm.getSelected() == null) {
        qm.setSelected("");
        qm.setResult(false);
      } else if (qm.getSelected() != null && qm.getSelected().equalsIgnoreCase(qm.getAnswer())) {
        diem++;
        qm.setResult(true);
      } else {
        qm.setResult(false);
      }
    }
    model.addAttribute("activeLink", "homework");
    model.addAttribute("pageTitle", "Kết quả");
    model.addAttribute("diem", diem + "/" + wrapper.getQuestions().size());
    model.addAttribute("wrapper", wrapper);
    return "ket-qua";
  }
}

package com.devculi.sway.controller.mvc;

import com.devculi.sway.business.shared.model.QuestionModel2;
import com.devculi.sway.business.shared.model.QuestionModelWrapper;
import com.devculi.sway.business.shared.model.SwaySubmitModel;
import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.interceptor.attr.annotations.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@HomePage
public class MainController {

  @GetMapping
  public String index(Model model) {
    model.addAttribute("activeLink", "index");
    return "gdmoi/trang-chu";
  }

  @GetMapping("/gioi-thieu")
  public String gioiThieu(Model model) {
    model.addAttribute("activeLink", "gt");
    model.addAttribute("pageTitle", "Giới thiệu");
    return "gdmoi/noi-dung";
  }

  @GetMapping("/doi-ngu")
  public String doiNgu(Model model) {
    model.addAttribute("activeLink", "doi-ngu");
    model.addAttribute("pageTitle", "Đội ngũ");
    return "gdmoi/noi-dung";
  }

  @GetMapping("/about-us")
  public String aboutUs(Model model) {
    model.addAttribute("activeLink", "about-us");
    model.addAttribute("pageTitle", "About us");
    return "gdmoi/tuyen-dung";
  }

  @GetMapping(value = {"/robots.txt", "/robot.txt"})
  @ResponseBody
  public String getRobotsTxt() {
    return "User-agent: *\n" + "Disallow: /admin\n";
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

  @GetMapping("/giaodienmoi/trang-chu")
  public String newIndex(Model model) {
    model.addAttribute("activeLink", "index");
    return "gdmoi/trang-chu";
  }

  @GetMapping("/giaodienmoi/noi-dung")
  public String newContent(Model model) {
    model.addAttribute("activeLink", "index");
    return "gdmoi/noi-dung";
  }

  @GetMapping("/giaodienmoi/dang-nhap")
  public String newDangNhap(Model model) {
    model.addAttribute("activeLink", "index");
    return "gdmoi/dang-nhap";
  }

  @GetMapping("/giaodienmoi/khct")
  public String khoaHocChiTiet(Model model) {
    model.addAttribute("activeLink", "index");
    return "gdmoi/khoa-hoc-chi-tiet";
  }

  @GetMapping("/giaodienmoi/bt")
  public String baitapChiTiet(Model model) {
    List<Question> qs = new ArrayList<>();
    qs.add(
        new Question((long) 1, "A###DEVCULI###B###DEVCULI###C", "A", "ABCD?", "asd", true, "123"));
    qs.add(
        new Question((long) 2, "A###DEVCULI###B###DEVCULI###C", "A", "ABCD?", "asd", true, "124"));
    SwayTestModel swayTestModel = new SwayTestModel();
    swayTestModel.setQuestions(
        qs.stream().map(Entity2DTO::question2DTO).collect(Collectors.toList()));
    swayTestModel.setTestName("Tên bài test");
    SwaySubmitModel submitModel = new SwaySubmitModel();
    submitModel.setSwayTest(swayTestModel);
    model.addAttribute("activeLink", "index");
    model.addAttribute("swaySubmit", submitModel);
    model.addAttribute("swayTest", swayTestModel);
    return "gdmoi/bai-tap";
  }

  @PostMapping("/giaodienmoi/kq")
  public String ketquaSubmit(@ModelAttribute(name = "swayTest") SwayTestModel swayTestModel, Model model) {
    model.addAttribute("activeLink", "index");
    System.out.println(swayTestModel);
    model.addAttribute("swaySubmit", swayTestModel);
    return "gdmoi/ket-qua";
  }

  @GetMapping("/giaodienmoi/co-hoi-viec-lam")
  public String chvl(Model model) {
    model.addAttribute("activeLink", "index");
    return "gdmoi/tuyen-dung";
  }
}

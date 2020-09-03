package com.devculi.sway.controller.mvc;

import com.devculi.sway.business.shared.model.QuestionModel2;
import com.devculi.sway.business.shared.model.QuestionModelWrapper;
import com.devculi.sway.interceptor.attr.annotations.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    model.addAttribute("pageTitle", "Giới thiệu");
    return "gdmoi/noi-dung";
  }

  @GetMapping("/doi-ngu")
  public String doiNgu(Model model) {
    model.addAttribute("pageTitle", "Đội ngũ");
    return "gdmoi/noi-dung";
  }

  @GetMapping("/co-hoi-viec-lam")
  public String aboutUs(Model model) {
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

    model.addAttribute("pageTitle", "Nội dung");
    return "gdmoi/noi-dung";
  }

  @GetMapping("/giaodienmoi/khct")
  public String khoaHocChiTiet(Model model) {

    model.addAttribute("pageTitle", "Khoá học chi tiết");
    return "gdmoi/khoa-hoc-chi-tiet";
  }

  @GetMapping("/giaodienmoi/bt")
  public String baitapChiTiet(Model model) {

    model.addAttribute("pageTitle", "Bài tập về nhà");
    return "gdmoi/bai-tap";
  }

  @GetMapping("/giaodienmoi/kq")
  public String ketquaSubmit(Model model) {

    model.addAttribute("pageTitle", "Kết quả");
    return "gdmoi/ket-qua";
  }

  @GetMapping("/giaodienmoi/co-hoi-viec-lam")
  public String chvl(Model model) {

    model.addAttribute("pageTitle", "Cơ hội việc làm");
    return "gdmoi/tuyen-dung";
  }
}

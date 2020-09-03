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

  @GetMapping("/bai-tap-lop")
  public String homework(Model model) {
    model.addAttribute("pageTitle", "Bài tập về nhà");
    return "gdmoi/bai-tap";
  }

  @GetMapping("/test-online")
  public String testOnline(Model model) {
    model.addAttribute("pageTitle", "Test online");
    List<Question> qs = new ArrayList<>();
    qs.add(
        new Question((long) 1, "A###DEVCULI###B###DEVCULI###C", "A", "ABCD?", "asd", true, "123"));
    qs.add(
        new Question((long) 2, "A###DEVCULI###B###DEVCULI###C", "A", "ABCD?", "asd", true, "124"));
    QuestionModelWrapper wrapper = new QuestionModelWrapper();
    wrapper.setQuestions(qs);
    model.addAttribute("wrapper", wrapper);
    return "gdmoi/bai-tap";
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

  @GetMapping("/dang-nhap")
  public String dangNhap(Model model) {
    model.addAttribute("pageTitle", "Đăng nhập");
    return "gdmoi/dang-nhap";
  }

  @GetMapping("/giaodienmoi/dang-nhap")
  public String newDangNhap(Model model) {
    model.addAttribute("pageTitle", "Đăng nhập");
    return "gdmoi/dang-nhap";
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

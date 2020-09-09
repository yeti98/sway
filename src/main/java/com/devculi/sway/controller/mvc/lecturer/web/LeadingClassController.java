package com.devculi.sway.controller.mvc.lecturer.web;

import com.devculi.sway.annotations.RequireRoleLecturer;
import com.devculi.sway.business.shared.model.SubmitStatModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.*;
import com.devculi.sway.manager.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quan-ly-lop-hoc")
@RequireRoleLecturer
public class LeadingClassController {
  @Autowired ILecturerService lecturerService;
  @Autowired IClassService classService;
  @Autowired ILessonService lessonService;
  @Autowired ISwayTestService swayTestService;
  @Autowired IUserService userService;
  @Autowired ISubmitService submitService;

  @GetMapping
  public String renderManageSwayClassView(Model model) {
    List<SwayClass> leadingClasses = lecturerService.getLeadingClasses();
    model.addAttribute(
        "classes", leadingClasses.stream().map(Entity2DTO::class2DTO).collect(Collectors.toList()));
    return "giaovien/quan-ly-lop-hoc/index";
  }

  @GetMapping("/{classSlug}/{lessonSlug}")
  public String renderLessonStatDetail(
      Model model,
      @PathVariable("classSlug") String classSlug,
      @PathVariable("lessonSlug") String lessonSlug)
      throws Exception {
    SwayClass classBySlug = classService.getClassBySlug(classSlug);
    System.out.println("Class id: \t" + classBySlug.getId());
    SwayUser currentUser = userService.getCurrentUser();
    System.out.println(currentUser.getUsername());
    if (classBySlug.getLecturers().stream()
        .noneMatch(user -> user.getUsername().equalsIgnoreCase(currentUser.getUsername()))) {
      System.out.println("Chưa đăng ký lớp học");
      model.addAttribute("error", true);
      model.addAttribute("errorMessage", "Bạn không dạy lớp học này");
      return "giaovien/quan-ly-lop-hoc/bai-hoc/index";
    }
    Lesson lessonBySlug = lessonService.getLessonBySlug(lessonSlug);
    List<Lesson> lessonList = classBySlug.getCourse().getLessons();

    if (!lessonList.contains(lessonBySlug)) {
      model.addAttribute("error", true);
      model.addAttribute("errorMessage", "Bài học không tồn tại");
      return "giaovien/quan-ly-lop-hoc/bai-hoc/index";
    }

    List<SwayTest> testList = lessonBySlug.getTests();
    System.out.println(String.format("TEST SIZE: %s", testList.size()));
    List<List<SubmitStatModel>> statModelList = new ArrayList<>();
    for (SwayTest swayTest : testList) {

      List<SwaySubmit> submitList = submitService.getAllSubmitsOfTest(swayTest, classBySlug);
      System.out.println("\n\n\nSUBMITS: ");

      for (SwaySubmit submit : submitList) {
        System.out.println(submit.getSwayUser().getUsername());
        System.out.println(submit.getScoreInString());
        System.out.println("----------");
      }

      List<SwayUser> students = classBySlug.getStudents();
      List<SubmitStatModel> data = analyzeSubmitList(submitList, students);
      statModelList.add(data);
    }
    model.addAttribute("class", Entity2DTO.class2DTO(classBySlug));
    model.addAttribute("totalTest", testList.size());
    model.addAttribute(
        "tests", testList.stream().map(Entity2DTO::swayTest2DTO).collect(Collectors.toList()));
    model.addAttribute("statsData", statModelList);
    return "giaovien/quan-ly-lop-hoc/bai-hoc/index";
  }

  private List<SubmitStatModel> analyzeSubmitList(
      List<SwaySubmit> submitList, List<SwayUser> students) {
    List<SubmitStatModel> result = new ArrayList<>();
    for (SwayUser student : students) {
      SubmitStatModel statModel = new SubmitStatModel();
      statModel.setStudent(Entity2DTO.user2DTO(student));

      List<SwaySubmit> submitsByStudent =
          submitList
              .parallelStream()
              .filter(
                  swaySubmit -> {
                    return swaySubmit.getSwayUser().equals(student);
                  })
              .collect(Collectors.toList());
      int totalSubmit = submitsByStudent.size();
      statModel.setTotalAttempt(totalSubmit);
      if (totalSubmit == 0) {
        statModel.setMaxScore(0.0);
      } else {
        Optional<Double> maxScore =
            submitsByStudent.stream().map(SwaySubmit::getScore).max(Double::compare);
        System.out.println(String.format("MAX SCORE: %s", maxScore.get()));
        statModel.setMaxScore(maxScore.get());
      }
      boolean isPassed = submitsByStudent.parallelStream().anyMatch(SwaySubmit::isPassed);
      statModel.setPassed(isPassed);

      result.add(statModel);
    }
    return result;
  }
}

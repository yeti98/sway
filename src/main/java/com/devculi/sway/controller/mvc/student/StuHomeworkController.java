package com.devculi.sway.controller.mvc.student;

import com.devculi.sway.annotations.RequireRoleStudent;
import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.*;
import com.devculi.sway.manager.service.interfaces.*;
import com.devculi.sway.manager.service.threadpool.MainExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bai-tap-lop")
@RequireRoleStudent
public class StuHomeworkController {
  @Autowired IClassService classService;
  @Autowired ISwayTestService swayTestService;
  @Autowired ILessonService lessonService;
  @Autowired IUserService userService;
  @Autowired ISubmitService submitService;

  @GetMapping
  public String renderIndex(Model model) {
    List<SwayClass> joinedClasses = userService.getJoinedClasses();
    model.addAttribute(
        "classes", joinedClasses.stream().map(Entity2DTO::class2DTO).collect(Collectors.toList()));
    return "student/bai-tap-lop/index";
  }

  @GetMapping("/{classSlug}")
  public String renderLessonOfClass(Model model, @PathVariable(name = "classSlug") String classSlug) {
    SwayClass classBySlug = classService.getClassBySlug(classSlug);
    List<Lesson> lessonList = classBySlug.getCourse().getLessons();
    model.addAttribute("class", classBySlug);
    model.addAttribute("lessons", lessonList.stream().map(Entity2DTO::lesson2DTO).collect(Collectors.toList()));
    return "student/bai-tap-lop/lop-hoc/index";
  }

  @GetMapping("/{classSlug}/{lessonSlug}/{testSlug}")
  public String renderTestDetail(
      Model model,
      @PathVariable("classSlug") String classSlug,
      @PathVariable("lessonSlug") String lessonSlug,
      @PathVariable("testSlug") String testSlug)
      throws Exception {
    SwayClass classBySlug = classService.getClassBySlug(classSlug);
    System.out.println("Class id: \t" + classBySlug.getId());
    if (!classService.isRegistered(classBySlug)) {
      System.out.println("Chưa đăng ký lớp học");
      model.addAttribute("error", true);
      model.addAttribute("errorMessage", "Bạn chưa đăng kí lớp học này");
      return "student/bai-tap-lop/bai-tap";
    }
    System.out.println("Đã đăng ký lớp học");
    Lesson lessonBySlug = lessonService.getLessonBySlug(lessonSlug);
    List<Lesson> lessonList = classBySlug.getCourse().getLessons();

    if (!lessonList.contains(lessonBySlug)) {
      model.addAttribute("error", true);
      model.addAttribute("errorMessage", "Bài học không tồn tại");
      return "student/bai-tap-lop/bai-tap";
    }
    Lesson prvLesson = getPreviousLesson(lessonList, lessonBySlug);
    if (prvLesson != null) { // Có bài học trước : kiểm tra xem đã qua chưa
      SwayUser currentUser = userService.getCurrentUser();
      boolean isPassed = lessonService.isPassedLesson(currentUser, classBySlug, prvLesson);
      if (!isPassed) { // nếu chưa qua: yêu cầu làm bài tập trước
        model.addAttribute("error", true);
        String prvLessonLink = String.format("/bai-tap-lop/%s/%s", classSlug, prvLesson.getSlug());
        model.addAttribute(
            "errorMessage",
            String.format("Bạn cần hoàn thành <a href='%s'>bài học trước</a>", prvLessonLink));
        return "student/bai-tap-lop/bai-tap";
      }
    }
    // Đã qua bài tập trước hoặc không có bài tập trước
    SwayTest test = swayTestService.getTestBySlug(testSlug);
    SwayTestModel testModel = Entity2DTO.swayTest2DTO(test);
    model.addAttribute("activeLink", "index");
    //    model.addAttribute("swaySubmit", submitModel);
    model.addAttribute("swayTest", testModel);
    model.addAttribute("swayClass", Entity2DTO.class2DTO(classBySlug));
    model.addAttribute("lesson", Entity2DTO.lesson2DTO(lessonBySlug));
    return "student/bai-tap-lop/bai-tap";
  }

  private Lesson getPreviousLesson(List<Lesson> lessonList, Lesson lessonBySlug) {
    int index = lessonList.indexOf(lessonBySlug);
    if (index == -1) return null;
    if (index == 0) return null;
    return lessonList.get(index - 1);
  }

  @PostMapping("/nop-bai")
  public String submitTest(
      @ModelAttribute(name = "swayTest") SwayTestModel submittedTestModel,
      Model model,
      @RequestParam("class") Long classId,
      @RequestParam("lesson") Long lessonId)
      throws Exception {
    model.addAttribute("swaySubmit", submittedTestModel);
    Long id = submittedTestModel.getId();
    if (classId == null || lessonId == null) {
      model.addAttribute("error", true);
      model.addAttribute("errorMessage", "Quá trình chấm bài xảy ra lỗi");
    } else {
      SwayClass currentClass = classService.getClassById(classId);
      Lesson currentLesson = lessonService.getLessonByID(lessonId);
      SwayUser currentUser = userService.getCurrentUser();
      if (id != null) {
        SwayTest currentTest = swayTestService.getTestByID(id);
        if (!currentTest.getId().equals(id)) {
          return null;
        }
        CompletableFuture<SwaySubmit> future =
            CompletableFuture.supplyAsync(
                    () -> {
                      try {
                        return swayTestService.countCorrectAnswer(submittedTestModel, currentTest);
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                      return 0;
                    },
                    MainExecutor.getInstance())
                .thenApplyAsync(
                    numberOfCorrectAns -> {
                      try {
                        return submitService.saveSubmittedHomework(
                            currentUser,
                            currentClass,
                            currentLesson,
                            currentTest,
                            numberOfCorrectAns);
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                      return null;
                    },
                    MainExecutor.getInstance());
        SwaySubmit result = null;
        result = future.get();
        if (result == null) {
          throw new Exception("Quá trình chấm bài xảy ra lỗi");
        }

        model.addAttribute("scoreInString", result.getScoreInString());
        model.addAttribute("score", result.getScore());
        model.addAttribute(
            "isPassedMessage",
            result.isPassed()
                ? "Chúc mừng bạn đã hoàn thành bài tập"
                : "Vui lòng làm bài tập nghiêm túc hơn");
        model.addAttribute("swayTest", submittedTestModel);
      }
    }
    return "student/bai-tap-lop/ket-qua";
  }
}

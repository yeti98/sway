package com.devculi.sway.controller.mvc.student;

import com.devculi.sway.annotations.RequireRoleStudent;
import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.manager.service.interfaces.IClassService;
import com.devculi.sway.manager.service.interfaces.ILessonService;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import com.devculi.sway.manager.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/bai-tap-lop")
@RequireRoleStudent
public class StuHomeworkController {
  @Autowired IClassService classService;
  @Autowired ISwayTestService swayTestService;
  @Autowired
  ILessonService lessonService;
  @Autowired
  IUserService userService;

  private HashMap<Long, SwayClassModel> classesMap = new HashMap<>();

  @GetMapping("/{classSlug}/{lessonSlug}/{testSlug}")
  public String renderHomework(Model model,
                               @PathVariable("classSlug") String classSlug,
                               @PathVariable("lessonSlug") String lessonSlug,
                               @PathVariable("testSlug") String testSlug) throws Exception {
    SwayClass swayClass = classService.getClassBySlug(classSlug);
    if (!classService.isRegistered(swayClass)) {
      model.addAttribute("error", true);
      model.addAttribute("errorMessage", "Bạn chưa đăng kí lớp học này");
      return "redirect:/error";
    }
    Lesson lessonBySlug = lessonService.getLessonBySlug(lessonSlug);
    List<Lesson> lessonList = swayClass.getCourse().getLessons();

    if (!lessonList.contains(lessonBySlug)) {
      model.addAttribute("error", true);
      model.addAttribute("errorMessage", "Bài học không tồn tại");
      return "redirect:/error";
    }
    Lesson prvLesson = getPreviousLesson(lessonList, lessonBySlug);
    if (prvLesson != null) {
      SwayUser currentUser = userService.getCurrentUser();
      boolean isPassed = lessonService.isPassedLesson(currentUser, prvLesson);
    }
//    if ()
//    SwayTest swayTest = swayTestService.getTestBySlug(testSlug);
//    swayTestService.isNextTest(swayClass, swayTest);
    return "student/bai-tap-lop/bai-tap";
  }

  private Lesson getPreviousLesson(List<Lesson> lessonList, Lesson lessonBySlug) {
    int index = lessonList.indexOf(lessonBySlug);
    if (index == -1) return null;
    if (index == 0) return null;
    return lessonList.get(index -1);
  }

//  @GetMapping("/{id}")
//  public String viewHomeworkDetail(Model model, @PathVariable(name = "id") Long id) {
//    SwayClassModel classModel = classesMap.getOrDefault(id, null);
//    System.out.println(classModel);
//    if (classModel != null) {
//      CourseModel course = classModel.getCourse();
//      if (course != null) {
//        model.addAttribute("courseId", course.getId());
//        model.addAttribute("courseName", course.getName());
//        model.addAttribute("lessons", course.getLessons());
//      } else {
//        model.addAttribute("lessons", new ArrayList<>());
//      }
//    } else {
//      return "redirect:/bai-tap-lop";
//    }
//    return "student/baitaplop/detail";
//  }

//  @GetMapping("/{id}/{lId}/{tId}")
//  public String viewTestDetail(
//      Model model,
//      @PathVariable(name = "id") Long classID,
//      @PathVariable(name = "lId") Long lessonId,
//      @PathVariable(name = "tId") Long testId) {
//    SwayClassModel classModel = classesMap.getOrDefault(classID, null);
//    if (classModel != null) {
//      CourseModel course = classModel.getCourse();
//      if (course != null) {
//        LessonModel lesson = getLessonById(course, lessonId);
//        if (lesson == null) {
//          return "redirect:/error";
//        }
//        SwayTestModel testModel = getTestById(lesson, testId);
//        if (testModel == null) {
//          return "redirect:/error";
//        }
//        model.addAttribute("courseName", course.getName());
//        model.addAttribute("lessonName", lesson.getName());
//        model.addAttribute("questions", testModel.getQuestions());
//      } else {
//        model.addAttribute("lessons", new ArrayList<>());
//      }
//    } else {
//      return "redirect:/bai-tap-lop";
//    }
//    return "student/baitaplop/lesson/test/index";
//  }
//
//  private SwayTestModel getTestById(LessonModel lesson, Long testId) {
//    for (SwayTestModel testModel : lesson.getTests()) {
//      if (testModel.getId().equals(lesson.getId())) {
//        return testModel;
//      }
//    }
//    return null;
//  }
//
//  private LessonModel getLessonById(CourseModel course, Long lessonId) {
//    for (LessonModel lessonModel : course.getLessons()) {
//      if (lessonModel.getId().equals(lessonId)) {
//        return lessonModel;
//      }
//    }
//    return null;
//  }
}

package com.devculi.sway.controller.mvc.student;

import com.devculi.sway.annotations.RequireRoleStudent;
import com.devculi.sway.business.shared.model.CourseModel;
import com.devculi.sway.business.shared.model.LessonModel;
import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.manager.service.interfaces.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bai-tap-lop")
@RequireRoleStudent
public class StuHomeworkController {
  @Autowired IClassService classService;

  private HashMap<Long, SwayClassModel> classesMap = new HashMap<>();

  @GetMapping
  public String renderHomework(Model model) {
    List<SwayClass> classList = classService.getJoinedClasses();
    List<SwayClassModel> swayClassModels = classList.stream().map(Entity2DTO::class2DTO).collect(Collectors.toList());
    for (SwayClassModel swayClassModel : swayClassModels) {
      classesMap.put(swayClassModel.getId(), swayClassModel);
    }
    model.addAttribute("classes", swayClassModels);
    return "student/baitaplop/index";
  }

  @GetMapping("/{id}")
  public String viewHomeworkDetail(Model model, @PathVariable(name = "id") Long id){
    SwayClassModel classModel = classesMap.getOrDefault(id, null);
    System.out.println(classModel);
    if (classModel != null) {
      CourseModel course = classModel.getCourse();
      if (course != null) {
        model.addAttribute("courseId", course.getId());
        model.addAttribute("courseName", course.getName());
        model.addAttribute("lessons", course.getLessons());
      } else {
        model.addAttribute("lessons", new ArrayList<>());
      }
    } else {
      return "redirect:/bai-tap-lop";
    }
    return "student/baitaplop/detail";
  }

  @GetMapping("/{id}/{lId}/{tId}")
  public String viewTestDetail(Model model, @PathVariable(name = "id") Long classID, @PathVariable(name = "lId") Long lessonId, @PathVariable(name = "tId") Long testId){
    SwayClassModel classModel = classesMap.getOrDefault(classID, null);
    if (classModel != null) {
      CourseModel course = classModel.getCourse();
      if (course != null) {
        LessonModel lesson = getLessonById(course, lessonId);
        if (lesson == null) {
          return "redirect:/error";
        }
        SwayTestModel testModel = getTestById(lesson, testId);
        if (testModel == null) {
          return "redirect:/error";
        }
        model.addAttribute("courseName", course.getName());
        model.addAttribute("lessonName", lesson.getName());
        model.addAttribute("questions", testModel.getQuestions());
      } else {
        model.addAttribute("lessons", new ArrayList<>());
      }
    } else {
      return "redirect:/bai-tap-lop";
    }
    return "student/baitaplop/lesson/test/index";
  }

  private SwayTestModel getTestById(LessonModel lesson, Long testId) {
    for (SwayTestModel testModel : lesson.getTests()) {
      if (testModel.getId().equals(lesson.getId())){
        return testModel;
      }
    }
    return null;
  }

  private LessonModel getLessonById(CourseModel course, Long lessonId) {
    for (LessonModel lessonModel : course.getLessons()) {
      if (lessonModel.getId().equals(lessonId)){
        return lessonModel;
      }
    }
    return null;
  }

}

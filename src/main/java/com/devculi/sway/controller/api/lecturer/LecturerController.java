package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.controller.api.BaseController;
import com.devculi.sway.manager.service.interfaces.ILecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lecturers")
public class LecturerController extends BaseController {
  @Autowired ILecturerService lecturerService;

  @GetMapping("/classes/{id}")
  ResponseEntity getClassInfo(@PathVariable(name = "id") Long classId) {
    return ok(lecturerService.getClassById(classId));
  }

  //  @GetMapping("classes/{id}/questions")
  //  ResponseEntity getQuestionsInClass(
  //      @PathVariable(name = "id") Long classId,
  //      @RequestParam(name = "qpage", defaultValue = 0) Long qpage) {
  //    return ok(lecturerService.getQuestionInClass(classId, qpage));
  //  }

}

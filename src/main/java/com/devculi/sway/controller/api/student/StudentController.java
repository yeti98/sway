package com.devculi.sway.controller.api.student;

import com.devculi.sway.controller.mvc.BaseController;
import com.devculi.sway.manager.service.interfaces.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController extends BaseController {
  @Autowired IStudentService studentService;

  @GetMapping("/{id}")
  public ResponseEntity getStudentInfo(@PathVariable(name = "id") Long id) {
      return ok(studentService.getStudentByID(id));
  }

}

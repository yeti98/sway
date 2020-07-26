package com.devculi.sway.controller.student;

import com.devculi.sway.controller.BaseController;
import com.devculi.sway.manager.service.interfaces.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

@RestController
@RequestMapping("/students")
public class StudentController extends BaseController {
  @Autowired IStudentService studentService;

  @GetMapping("/{id}")
  public ResponseEntity getStudentInfo(@PathVariable(name = "id") Long id) {
    return ok(studentService.getStudentByID(id));
  }
}

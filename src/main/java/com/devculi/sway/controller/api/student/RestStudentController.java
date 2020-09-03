package com.devculi.sway.controller.api.student;

import com.devculi.sway.controller.api.RestBaseController;
import com.devculi.sway.manager.service.interfaces.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class RestStudentController extends RestBaseController {
  @Autowired IStudentService studentService;

//  @GetMapping("/{id}")
//  public ResponseEntity getStudentInfo(@PathVariable(name = "id") Long id) {
//    return ok(studentService.getStudentByID(id));
//  }
}

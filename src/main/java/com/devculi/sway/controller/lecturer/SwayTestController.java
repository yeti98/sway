package com.devculi.sway.controller.lecturer;

import com.devculi.sway.controller.BaseController;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tests")
public class SwayTestController extends BaseController {
  @Autowired ISwayTestService swayTestService;

  @GetMapping("/{id}")
  public ResponseEntity getTestByID(@PathVariable(name = "id") Long id) {
    return ok(swayTestService.getTestByID(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteTestByID(@PathVariable(name = "id") Long id) {
    return ok(swayTestService.deleteTestByID(id));
  }
}

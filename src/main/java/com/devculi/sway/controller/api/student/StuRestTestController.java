package com.devculi.sway.controller.api.student;

import com.devculi.sway.annotations.RequireRoleStudent;
import com.devculi.sway.controller.api.RestBaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
@RequireRoleStudent
public class StuRestTestController extends RestBaseController {

  @PostMapping
  public ResponseEntity submitTest() {
    return ok(null);
  }
}

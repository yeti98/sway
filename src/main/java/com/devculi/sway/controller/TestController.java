package com.devculi.sway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/filter")
public class TestController extends BaseController {

  @GetMapping
  public ResponseEntity getMapping() throws Exception {
    return ok(LocalDate.now().toString());
  }
}

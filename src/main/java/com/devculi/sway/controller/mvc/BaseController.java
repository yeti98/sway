package com.devculi.sway.controller.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("ALL")
public class BaseController<T> {

  public ResponseEntity ok(T body) {
    return ResponseEntity.ok(body);
  }

  public ResponseEntity error(HttpStatus status, T body) {
    return ResponseEntity.status(status).body(body);
  }
}

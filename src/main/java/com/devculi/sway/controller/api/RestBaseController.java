package com.devculi.sway.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class RestBaseController<T> {
  private static final Logger logger = Logger.getLogger(RestBaseController.class.getName());

  public ResponseEntity ok(T body) {
    return ResponseEntity.ok(body);
  }

  public ResponseEntity error(HttpStatus status, T body) {
    return ResponseEntity.status(status).body(body);
  }
}

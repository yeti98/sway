package com.devculi.sway.business.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = 176023227975132626L;

  private int httpStatusCode = HttpStatus.BAD_REQUEST.value();
  private String code;

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(String code, String message) {
    super(message);
    this.code = code;
  }

  public ValidationException(int httpStatusCode, String message) {
    super(message);
    this.httpStatusCode = httpStatusCode;
  }

  public int getHttpStatusCode() {
    return httpStatusCode;
  }

  public String getCode() {
    return code;
  }
}

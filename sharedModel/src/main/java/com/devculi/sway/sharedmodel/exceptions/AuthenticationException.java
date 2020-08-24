package com.devculi.sway.sharedmodel.exceptions;

public class AuthenticationException extends BaseRuntimeException {

  private static final long serialVersionUID = 176023227975132626L;
  private int HttpStatusCode = 401;
  private String code;

  public AuthenticationException(String message) {
    super(message);
  }

  public AuthenticationException(String code, String message) {
    super(message);
    this.code = code;
  }

  public int getHttpStatusCode() {
    return HttpStatusCode;
  }

  public String getCode() {
    return code;
  }
}

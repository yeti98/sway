package com.devculi.sway.sharedmodel.enums;

public enum LoginType {
  NORMAL("Normal"),
  CONTINUE_CHECK("2FA");

  private String type;

  LoginType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}

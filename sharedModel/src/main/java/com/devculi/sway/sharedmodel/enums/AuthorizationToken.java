package com.devculi.sway.sharedmodel.enums;

public enum AuthorizationToken {
  BEARER("Bearer"),
  BASIC("Basic"),
  OTT("Ott"); /*one time token*/

  private final String value;

  AuthorizationToken(String value) {
    this.value = value;
  }

  public static AuthorizationToken from(String text) {
    for (AuthorizationToken token : AuthorizationToken.values()) {
      if (token.value.equalsIgnoreCase(text)) {
        return token;
      }
    }
    return null;
  }

  public String value() {
    return this.value;
  }
}

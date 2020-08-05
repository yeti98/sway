package com.devculi.sway.sharedmodel.model;

import com.devculi.sway.sharedmodel.enums.AuthorizationToken;

public class AuthorizationTokenResult {

  private AuthorizationToken tokenEnum;
  private String token;
  private String identity;

  public AuthorizationTokenResult(AuthorizationToken tokenEnum, String token) {
    super();
    this.tokenEnum = tokenEnum;
    this.token = token;
  }

  public AuthorizationTokenResult(AuthorizationToken tokenEnum, String token, String identity) {
    super();
    this.tokenEnum = tokenEnum;
    this.token = token;
    this.identity = identity;
  }

  public String getIdentity() {
    return identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public AuthorizationToken getTokenEnum() {
    return tokenEnum;
  }

  public void setTokenEnum(AuthorizationToken tokenEnum) {
    this.tokenEnum = tokenEnum;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "AuthorizationTokenResult{"
        + "tokenEnum="
        + tokenEnum
        + ", token='"
        + token
        + '\''
        + ", identity='"
        + identity
        + '\''
        + '}';
  }
}

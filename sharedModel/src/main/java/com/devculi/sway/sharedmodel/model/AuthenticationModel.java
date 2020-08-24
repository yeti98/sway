package com.devculi.sway.sharedmodel.model;

public class AuthenticationModel {
  UserModel userModel;
  String token;
  String refreshToken;

  public AuthenticationModel() {}

  public void setUserModel(UserModel userModel) {
    this.userModel = userModel;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}

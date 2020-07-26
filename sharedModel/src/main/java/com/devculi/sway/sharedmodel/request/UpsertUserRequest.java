package com.devculi.sway.sharedmodel.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertUserRequest {
  private String name;
  private String username;
  private String avatar;
  private String description;
  private boolean status;
  private String type;
  private String password;

  public boolean isStatus() {
    return status;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UpsertUserRequest() {}

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}

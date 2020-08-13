package com.devculi.sway.sharedmodel.model;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel {
  private Long id;
  private String name;
  private String username;
  private String avatar;
  private String description;
  private boolean status;
  private String type;
  private String role;

  public UserModel() {}

  public UserModel(Long id, String name, String username, String avatar, String description, boolean status, String type, String role) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.avatar = avatar;
    this.description = description;
    this.status = status;
    this.type = type;
    this.role = role;
  }

  @Override
  public String toString() {
    return "{" +
            "\"id\":" + id +
            ", \"name\":\"" + name + '\"' +
            ", \"username\":\"" + username + '\"' +
            ", \"avatar\":\"" + avatar + '\"' +
            ", \"description\":\"" + description + '\"' +
            ", \"status\":\"" + status + '\"' +
            ", \"type\":\"" + type + '\"' +
            ", \"role\":\"" + role + '\"' +
            '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public boolean isStatus() {
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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}

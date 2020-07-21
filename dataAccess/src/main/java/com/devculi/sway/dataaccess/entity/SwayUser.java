package com.devculi.sway.dataaccess.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "susers")
public class SwayUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(nullable = false, updatable = false)
  private String username;

  private String avatar;
  private String description;
  private String status;
  private String type;

  @OneToMany private List<SwayClass> joinedClasses;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String saltValue;

  public SwayUser() {}

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSaltValue() {
    return saltValue;
  }

  public void setSaltValue(String saltValue) {
    this.saltValue = saltValue;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<SwayClass> getJoinedClasses() {
    return joinedClasses;
  }

  public void setJoinedClasses(List<SwayClass> joinedClasses) {
    this.joinedClasses = joinedClasses;
  }
}

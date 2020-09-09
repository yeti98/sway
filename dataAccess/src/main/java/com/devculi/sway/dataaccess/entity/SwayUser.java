package com.devculi.sway.dataaccess.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "susers")
public class SwayUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(nullable = false, updatable = false, unique = true)
  private String username;

  private String avatar;
  private String description;
  private boolean status;
  private String type;
  private String role;

  @JsonManagedReference
  @ManyToMany(mappedBy = "students")
  private List<SwayClass> joinedClasses;

  @JsonManagedReference
  @ManyToMany(mappedBy = "lecturers")
  private List<SwayClass> leadingClasses;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String saltValue;

  public SwayUser() {}

  public List<SwayClass> getLeadingClasses() {
    return leadingClasses;
  }

  public void setLeadingClasses(List<SwayClass> leadingClasses) {
    this.leadingClasses = leadingClasses;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

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

  public List<SwayClass> getJoinedClasses() {
    return joinedClasses;
  }

  public void setJoinedClasses(List<SwayClass> joinedClasses) {
    this.joinedClasses = joinedClasses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SwayUser)) return false;
    SwayUser user = (SwayUser) o;
    return status == user.status
        && id.equals(user.id)
        && Objects.equals(name, user.name)
        && username.equals(user.username)
        && role.equals(user.role)
        && createdAt.equals(user.createdAt)
        && Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, username, status, role, createdAt, password);
  }
}

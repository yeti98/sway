package com.devculi.sway.business.shared.model;

import com.devculi.sway.sharedmodel.model.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwayClassModel {

  private Long id;

  private String name;

  private Set<UserModel> lecturers;

  private String description;
  private List<UserModel> students;
  private CourseModel course;
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private boolean status;

  public SwayClassModel() {}

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

  public Set<UserModel> getLecturers() {
    return lecturers;
  }

  public void setLecturers(Set<UserModel> lecturers) {
    this.lecturers = lecturers;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<UserModel> getStudents() {
    return students;
  }

  public void setStudents(List<UserModel> students) {
    this.students = students;
  }

  public CourseModel getCourse() {
    return course;
  }

  public void setCourse(CourseModel course) {
    this.course = course;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }
}

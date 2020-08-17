package com.devculi.sway.business.shared.request;

import com.devculi.sway.business.shared.model.CourseModel;
import com.devculi.sway.business.shared.model.SwayTestModel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertLessonRequest {
  private Long id;
  private String name;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String lessonId;
  private CourseModel course;
  private List<SwayTestModel> tests;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public String getLessonId() {
    return lessonId;
  }

  public void setLessonId(String lessonId) {
    this.lessonId = lessonId;
  }

  public CourseModel getCourse() {
    return course;
  }

  public void setCourse(CourseModel course) {
    this.course = course;
  }

  public List<SwayTestModel> getTests() {
    return tests;
  }

  public void setTests(List<SwayTestModel> tests) {
    this.tests = tests;
  }
}

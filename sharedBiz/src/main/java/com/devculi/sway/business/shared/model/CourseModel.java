package com.devculi.sway.business.shared.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseModel {
  private long id;

  private String name;

  private List<LessonModel> lessons;

  private boolean status;

  public CourseModel() {}

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<LessonModel> getLessons() {
    return lessons;
  }

  public void setLessons(List<LessonModel> lessons) {
    this.lessons = lessons;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }
}

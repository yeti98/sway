package com.devculi.sway.business.shared.request;

import com.devculi.sway.business.shared.model.LessonModel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertCourseRequest {
  private long id;
  private String name;
  private List<LessonModel> lessons;
  private boolean active;
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}

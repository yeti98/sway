package com.devculi.sway.business.shared.model;

import com.devculi.sway.utils.GsonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseModel {
  private long id;

  private String name;

  private List<LessonModel> lessons;

  private String courseId;
  private boolean active;

  public CourseModel() {}

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

  public int getNumberOfLesson() {
    return lessons.size();
  }

  public String getJsonString() {
    return GsonUtils.toJson(this);
  }
}

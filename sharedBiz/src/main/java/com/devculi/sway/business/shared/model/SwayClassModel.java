package com.devculi.sway.business.shared.model;

import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.utils.GsonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwayClassModel {

  private Long id;

  private String name;

  private List<UserModel> lecturers;

  private String slug;
  private String classId;
  private String description;
  private List<UserModel> students;
  private CourseModel course;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private boolean active;
  private Double minScore;

  public SwayClassModel() {}

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public Double getMinScore() {
    return minScore;
  }

  public void setMinScore(Double minScore) {
    this.minScore = minScore;
  }

  public String getClassId() {
    return classId;
  }

  public void setClassId(String classId) {
    this.classId = classId;
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

  public List<UserModel> getLecturers() {
    return lecturers;
  }

  public void setLecturers(List<UserModel> lecturers) {
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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getJsonString() {
    return GsonUtils.toJson(this);
  }

  public int getNumberOfStudent() {
    return this.students.size();
  }

  public String getCourseName() {
    if (this.course != null) {
      return this.course.getName();
    }
    return "";
  }
}

package com.devculi.sway.business.shared.request;

import com.devculi.sway.business.shared.model.CourseModel;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertClassRequest {
  private Long id;
  private String name;
  private List<UserModel> lecturers;
  private String classId;
  private String description;
  private List<UserModel> students;
  private CourseModel course;
  private Double minScore;

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

  public String getClassId() {
    return classId;
  }

  public void setClassId(String classId) {
    this.classId = classId;
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

  public Double getMinScore() {
    return minScore;
  }

  public void setMinScore(Double minScore) {
    this.minScore = minScore;
  }
}

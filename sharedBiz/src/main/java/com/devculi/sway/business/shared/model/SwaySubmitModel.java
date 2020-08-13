package com.devculi.sway.business.shared.model;

import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwaySubmitModel {
  UserModel swayUser;
  private Long id;
  private String studentName;
  private String studentContact;
  private String studentNote;
  private TestType submitType;
  private String isChecked;
  private Double score;
  private String lecturerNote;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public UserModel getSwayUser() {
    return swayUser;
  }

  public void setSwayUser(UserModel swayUser) {
    this.swayUser = swayUser;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getStudentContact() {
    return studentContact;
  }

  public void setStudentContact(String studentContact) {
    this.studentContact = studentContact;
  }

  public String getStudentNote() {
    return studentNote;
  }

  public void setStudentNote(String studentNote) {
    this.studentNote = studentNote;
  }

  public TestType getSubmitType() {
    return submitType;
  }

  public void setSubmitType(TestType submitType) {
    this.submitType = submitType;
  }

  public String getIsChecked() {
    return isChecked;
  }

  public void setIsChecked(String isChecked) {
    this.isChecked = isChecked;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public String getLecturerNote() {
    return lecturerNote;
  }

  public void setLecturerNote(String lecturerNote) {
    this.lecturerNote = lecturerNote;
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
}

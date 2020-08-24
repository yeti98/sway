package com.devculi.sway.dataaccess.entity;

import com.devculi.sway.dataaccess.entity.enums.TestType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ssubmits")
public class SwaySubmit {
  @OneToOne SwayTest swayTest;
  @OneToOne SwayUser swayUser;
  @OneToOne SwayClass swayClass;
  @OneToOne SwayUser checkUser;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String studentName;
  private String studentContact;
  private String studentNote;
  private TestType submitType;

  private boolean isChecked;

  private boolean isPassed;

  private Double score;
  private String scoreInString;
  private String lecturerNote;
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;
  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public SwaySubmit() {}

  public String getScoreInString() {
    return scoreInString;
  }

  public void setScoreInString(String scoreInString) {
    this.scoreInString = scoreInString;
  }

  public SwayClass getSwayClass() {
    return swayClass;
  }

  public void setSwayClass(SwayClass swayClass) {
    this.swayClass = swayClass;
  }

  public SwayUser getCheckUser() {
    return checkUser;
  }

  public void setCheckUser(SwayUser checkUser) {
    this.checkUser = checkUser;
  }

  public boolean isChecked() {
    return isChecked;
  }

  public void setChecked(boolean checked) {
    isChecked = checked;
  }

  public boolean isPassed() {
    return isPassed;
  }

  public void setPassed(boolean passed) {
    isPassed = passed;
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

  public SwayTest getSwayTest() {
    return swayTest;
  }

  public void setSwayTest(SwayTest swayTest) {
    this.swayTest = swayTest;
  }

  public SwayUser getSwayUser() {
    return swayUser;
  }

  public void setSwayUser(SwayUser swayUser) {
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
}

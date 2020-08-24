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
  @OneToOne SwayUser checkUser;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String studentName;
  private String studentContact;
  private String studentNote;
  private TestType submitType;

  private String isChecked;
  private Double score;
  private String lecturerNote;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public SwaySubmit() {}

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

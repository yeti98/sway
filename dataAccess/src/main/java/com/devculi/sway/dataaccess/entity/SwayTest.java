package com.devculi.sway.dataaccess.entity;

import com.devculi.sway.dataaccess.entity.enums.Subject;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(
    name = "stests",
    indexes = {@Index(name = "stest_slug_idx", unique = true, columnList = "slug")})
public class SwayTest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String testId;

  private String testName;

  private String slug;

  @ManyToMany private Collection<Question> questions;

  private LocalDate deadline;
  private boolean active;
  private TestType testType;
  private Subject subject;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTestId() {
    return testId;
  }

  public void setTestId(String testId) {
    this.testId = testId;
  }

  public String getTestName() {
    return testName;
  }

  public void setTestName(String testName) {
    this.testName = testName;
  }

  public Collection<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(Collection<Question> questions) {
    this.questions = questions;
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public TestType getTestType() {
    return testType;
  }

  public void setTestType(TestType testType) {
    this.testType = testType;
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

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SwayTest)) return false;
    SwayTest swayTest = (SwayTest) o;
    return active == swayTest.active
        && id.equals(swayTest.id)
        && Objects.equals(testId, swayTest.testId)
        && Objects.equals(testName, swayTest.testName)
        && Objects.equals(slug, swayTest.slug)
        && Objects.equals(deadline, swayTest.deadline)
        && testType == swayTest.testType
        && subject == swayTest.subject
        && createdAt.equals(swayTest.createdAt)
        && Objects.equals(updatedAt, swayTest.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, testId, testName, slug, deadline, active, testType, subject, createdAt, updatedAt);
  }
}

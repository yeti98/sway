package com.devculi.sway.business.shared.model;

import com.devculi.sway.dataaccess.entity.enums.Subject;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.utils.GsonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwayTestModel {
  private Long id;

  private String testId;
  private Subject subject;

  private String testName;

  private List<QuestionModel> questions;

  private LocalDate deadline;
  private boolean active;
  private TestType testType;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private String slug;

  public SwayTestModel() {}

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
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

  public List<QuestionModel> getQuestions() {
    return questions;
  }

  public void setQuestions(List<QuestionModel> questions) {
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

  public int getNumberOfQuestion() {
    return questions.size();
  }

  public String getJsonString() {
    return GsonUtils.toJson(this);
  }
}

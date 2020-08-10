package com.devculi.sway.business.shared.request;

import com.devculi.sway.business.shared.model.QuestionModel;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertTestRequest {
  private Long id;

  private String testId;

  private String testName;

  private Collection<QuestionModel> questions;

  private LocalDate deadline;
  private boolean active;
  private TestType testType;

  public UpsertTestRequest() {}

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

  public Collection<QuestionModel> getQuestions() {
    return questions;
  }

  public void setQuestions(Collection<QuestionModel> questions) {
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
}

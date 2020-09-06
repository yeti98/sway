package com.devculi.sway.business.shared.model;

import com.devculi.sway.sharedmodel.model.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitStatModel {

  private UserModel student;

  private int totalAttempt;

  private boolean isPassed;

  private double maxScore;

  public UserModel getStudent() {
    return student;
  }

  public void setStudent(UserModel student) {
    this.student = student;
  }

  public int getTotalAttempt() {
    return totalAttempt;
  }

  public void setTotalAttempt(int totalAttempt) {
    this.totalAttempt = totalAttempt;
  }

  public boolean isPassed() {
    return isPassed;
  }

  public void setPassed(boolean passed) {
    isPassed = passed;
  }

  public double getMaxScore() {
    return maxScore;
  }

  public void setMaxScore(double maxScore) {
    this.maxScore = maxScore;
  }
}

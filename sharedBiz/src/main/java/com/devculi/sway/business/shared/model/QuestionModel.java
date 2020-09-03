package com.devculi.sway.business.shared.model;

import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.utils.GsonUtils;

import java.time.LocalDateTime;

public class QuestionModel {
  private Long id;
  private String choices;
  private String answer;
  private String content;
  private String explanation;
  private boolean active;
  private String questionId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String selected;
  private boolean isWrong;

  public QuestionModel() {}

  public boolean isWrong() {
    return isWrong;
  }

  public void setWrong(boolean wrong) {
    isWrong = wrong;
  }

  public String getSelected() {
    return selected;
  }

  public void setSelected(String selected) {
    this.selected = selected;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getChoices() {
    return choices;
  }

  public void setChoices(String choices) {
    this.choices = choices;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getQuestionId() {
    return questionId;
  }

  public void setQuestionId(String questionId) {
    this.questionId = questionId;
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

  public String getReadableChoices() {
    return String.join(" ", choices.split(Question.DETERMINER));
  }

  public String getJsonString() {
    return GsonUtils.toJson(this);
  }

  public String[] getChoicesList() {
    return choices.split(Question.DETERMINER);
  }

  @Override
  public String toString() {
    return "QuestionModel{"
        + "id="
        + id
        + ", choices='"
        + choices
        + '\''
        + ", answer='"
        + answer
        + '\''
        + ", content='"
        + content
        + '\''
        + ", explanation='"
        + explanation
        + '\''
        + ", active="
        + active
        + ", questionId='"
        + questionId
        + '\''
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + ", selected='"
        + selected
        + '\''
        + '}';
  }

  public String getBackground(String choice) {
    if (!choice.equalsIgnoreCase(this.selected)) return "";
    if (this.isWrong) return "background-color: lightcoral";
    return "background-color: lightgreen";
  }
}

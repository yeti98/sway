package com.devculi.sway.sharedmodel.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertQuestionRequest {
  private Long id;
  private List<String> choices;
  private String answer;
  private String content;
  private String explanation;
  private boolean active;
  private String questionId;

  public UpsertQuestionRequest() {}

  @Override
  public String toString() {
    return "UpsertQuestionRequest{"
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
        + '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<String> getChoices() {
    return choices;
  }

  public void setChoices(List<String> choices) {
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
}

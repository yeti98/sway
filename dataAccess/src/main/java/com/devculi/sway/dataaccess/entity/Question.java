package com.devculi.sway.dataaccess.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "question")
  private List<Choice> choices;

  @OneToOne private Choice answer;
  private String hint;
  private String explanation;
  private byte status;
}

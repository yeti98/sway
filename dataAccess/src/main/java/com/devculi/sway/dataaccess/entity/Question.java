package com.devculi.sway.dataaccess.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}

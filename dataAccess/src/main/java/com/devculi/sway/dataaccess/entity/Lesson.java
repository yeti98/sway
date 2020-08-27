package com.devculi.sway.dataaccess.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "slessons")
public class Lesson {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private String lessonId;
  private String slug;
  private boolean active;
  @ManyToMany private List<SwayTest> tests;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public Lesson() {}

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getLessonId() {
    return lessonId;
  }

  public void setLessonId(String lessonId) {
    this.lessonId = lessonId;
  }

  public List<SwayTest> getTests() {
    return tests;
  }

  public void setTests(List<SwayTest> tests) {
    this.tests = tests;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Lesson)) return false;
    Lesson lesson = (Lesson) o;
    return isActive() == lesson.isActive()
        && getId().equals(lesson.getId())
        && Objects.equals(getName(), lesson.getName())
        && Objects.equals(getLessonId(), lesson.getLessonId())
        && getSlug().equals(lesson.getSlug())
        && getCreatedAt().equals(lesson.getCreatedAt());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getLessonId(), getSlug(), isActive(), getCreatedAt());
  }
}

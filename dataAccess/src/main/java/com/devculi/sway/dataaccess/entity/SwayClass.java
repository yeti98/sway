package com.devculi.sway.dataaccess.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
    name = "sclasses",
    indexes = {@Index(name = "sclass_slug_idx", unique = true, columnList = "slug")})
public class SwayClass {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String slug;
  private String classId;
  private Double minScore;

  @ManyToMany
  @JoinTable(
      name = "sclass_lecturers",
      joinColumns = {@JoinColumn(name = "suser_id")},
      inverseJoinColumns = {@JoinColumn(name = "sclass_id")})
  private List<SwayUser> lecturers;

  private String description;

  @ManyToMany
  @JoinTable(
      name = "sclass_students",
      joinColumns = {@JoinColumn(name = "suser_id")},
      inverseJoinColumns = {@JoinColumn(name = "sclass_id")})
  private List<SwayUser> students;

  @OneToOne private Course course;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  private boolean active;

  public SwayClass() {}

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getClassId() {
    return classId;
  }

  public void setClassId(String classId) {
    this.classId = classId;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
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

  public List<SwayUser> getLecturers() {
    return lecturers;
  }

  public void setLecturers(List<SwayUser> lecturers) {
    this.lecturers = lecturers;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public boolean getStatus() {
    return active;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<SwayUser> getStudents() {
    return students;
  }

  public void setStudents(List<SwayUser> students) {
    this.students = students;
  }

  public Double getMinScore() {
    return minScore;
  }

  public void setMinScore(Double minScore) {
    this.minScore = minScore;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SwayClass)) return false;
    SwayClass swayClass = (SwayClass) o;
    return isActive() == swayClass.isActive()
        && getId().equals(swayClass.getId())
        && Objects.equals(getName(), swayClass.getName())
        && Objects.equals(getSlug(), swayClass.getSlug())
        && Objects.equals(getClassId(), swayClass.getClassId())
        && getCreatedAt().equals(swayClass.getCreatedAt());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getSlug(), getClassId(), getCreatedAt(), isActive());
  }
}

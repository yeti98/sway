package com.devculi.sway.dataaccess.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sclasses")
public class SwayClass {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany
  @JoinTable(
      name = "sclass_lecturers",
      joinColumns = {@JoinColumn(name = "suser_id")},
      inverseJoinColumns = {@JoinColumn(name = "sclass_id")})
  private Set<SwayUser> lecturers;

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

  private boolean status;

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

  public SwayClass() {}

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

  public Set<SwayUser> getLecturers() {
    return lecturers;
  }

  public void setLecturers(Set<SwayUser> lecturers) {
    this.lecturers = lecturers;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
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
}

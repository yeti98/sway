package com.devculi.sway.dataaccess.entity;

import javax.persistence.*;
import java.util.List;

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
  private List<SwayUser> lecturers;

  private String description;

  @ManyToMany
  @JoinTable(
      name = "sclass_students",
      joinColumns = {@JoinColumn(name = "suser_id")},
      inverseJoinColumns = {@JoinColumn(name = "sclass_id")})
  private List<SwayUser> students;

  @OneToMany
  @JoinTable(
          name = "sclass_students",
          joinColumns = {@JoinColumn(name = "suser_id")},
          inverseJoinColumns = {@JoinColumn(name = "sclass_id")})
  private List<SwayUser> lessons;

  private byte status;

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

  public List<SwayUser> getLecturers() {
    return lecturers;
  }

  public void setLecturers(List<SwayUser> lecturers) {
    this.lecturers = lecturers;
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

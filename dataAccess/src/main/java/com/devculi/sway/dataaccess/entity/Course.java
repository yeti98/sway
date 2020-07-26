package com.devculi.sway.dataaccess.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @OneToMany(mappedBy = "course")
  private List<Lesson> lessons;

  private byte status;
}

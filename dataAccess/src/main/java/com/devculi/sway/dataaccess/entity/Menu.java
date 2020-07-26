package com.devculi.sway.dataaccess.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu")
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private byte hidden;

  @Column(name = "mIndex")
  private int index;

  @OneToMany
  @JoinTable(
      name = "menu_posts",
      joinColumns = @JoinColumn(name = "menu_id"),
      inverseJoinColumns = @JoinColumn(name = "post_id"))
  private List<Post> posts;
}

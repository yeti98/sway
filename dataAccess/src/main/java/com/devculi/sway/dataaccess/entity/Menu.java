package com.devculi.sway.dataaccess.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String hidden; // y/n

    private int index;  // số thứ tự

    @OneToMany(mappedBy = "menu")
    private List<Post> posts;
}

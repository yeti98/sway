package com.devculi.sway.dataaccess.entity;


import javax.persistence.*;

@Entity
@Table(name = "slessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name="id_Course", nullable = false)
    private Course course;
}

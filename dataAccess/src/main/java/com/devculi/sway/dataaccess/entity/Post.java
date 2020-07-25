package com.devculi.sway.dataaccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String contents;

    @OneToOne
    @JoinColumn(name = "id_user")
    private SwayUser author;


    @ManyToOne
    @JoinColumn(name = "id_menu")
    private Menu menu;

}

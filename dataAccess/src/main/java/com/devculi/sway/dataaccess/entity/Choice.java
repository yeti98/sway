package com.devculi.sway.dataaccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "choices")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private String cText;

    @OneToOne
    private Question question;
}

package com.devculi.sway.dataaccess.entity;

import com.devculi.sway.dataaccess.entity.enums.TestType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "stests")
public class SwayTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Collection<Question> questions;

    private LocalDate deadline;
    private String status;
    private TestType testType;

    @OneToMany
    @JoinTable(name = "stest_submits")
    private Collection<SwaySubmit> submits;
}

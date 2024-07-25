package com.app.spendWise.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "advice")
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adviceId;

    @Column(length = 30)
    private String userId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column
    private String title;

    @Column
    private String problem;

    @Column
    private String advice;
}

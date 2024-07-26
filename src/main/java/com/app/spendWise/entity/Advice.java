package com.app.spendWise.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "advice")
@Getter @Setter
@ToString
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 30)
    private String userId;

    @Column
    private int paymentId;

    @Column
    private boolean status;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column
    private String title;

    @Column
    private String problem;

    @Column
    private String advice;
}

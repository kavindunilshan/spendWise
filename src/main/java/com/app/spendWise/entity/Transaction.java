package com.app.spendWise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter @Setter
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", insertable = false, updatable = false)
    private Category category;

    @Column
    private Integer category_id;

    @ManyToOne
    @JoinColumn(name = "custom_category_id", referencedColumnName = "customCategoryId", insertable = false, updatable = false)
    private CustomCategory customCategory;

    @Column
    private Integer custom_category_id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;

    @Column(length = 30)
    private String user_id;

    @Column()
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private double amount;
}
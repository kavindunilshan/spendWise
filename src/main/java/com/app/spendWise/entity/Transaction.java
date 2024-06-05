package com.app.spendWise.entity;

import com.app.spendWise.utils.CategoryType;
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
    @JoinColumn(name = "cat_id", referencedColumnName = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "custom_cat_id", referencedColumnName = "customCategoryId")
    private CustomCategory customCategory;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CategoryType categoryType;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 255)
    private String description;
}

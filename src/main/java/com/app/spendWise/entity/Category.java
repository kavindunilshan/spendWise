package com.app.spendWise.entity;

import com.app.spendWise.utils.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "category")
@Getter @Setter
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column(length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CategoryType type;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;
}

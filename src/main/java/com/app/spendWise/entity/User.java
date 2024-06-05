package com.app.spendWise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter @Setter
@ToString
public class User {
    @Id
    @Column(length = 30)
    private Long userId;

    @Column
    private String name;

    @Column
    private String email;
}

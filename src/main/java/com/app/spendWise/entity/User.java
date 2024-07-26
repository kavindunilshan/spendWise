package com.app.spendWise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="`user`")
@Getter @Setter
@ToString
public class User {
    @Id
    @Column(length = 30)
    private String userId;

    @Column
    private String name;

    @Column
    private String email;

    @Column()
    private String currency;

    @Column()
    private String country;

    @Column()
    private boolean isAdmin;
}

package com.app.spendWise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="`user`")
@Getter
@Setter
@ToString
public class Preferences {
    // userId, currency, country, darkMode

    @Id
    @Column(length = 30)
    private String userId;

    @Column()
    private Boolean isDarkMode;

    @Column()
    private String isIncomePieChart;

    @Column()
    private String isExpensePieChart;
}

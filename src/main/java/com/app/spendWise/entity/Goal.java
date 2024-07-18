package com.app.spendWise.entity;

import com.app.spendWise.utils.CategoryType;
import com.app.spendWise.utils.DataViewPeriod;
import com.app.spendWise.utils.GoalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "goal")
@Getter
@Setter
@ToString
public class Goal {
    // column names ==> goal_id, user_id, amount, period (monthly, yearly), goaltype, categorytype, categoryid

    @Column
    @Id
    private int goalId;

    @Column(length = 30)
    private String user_id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    private double amount;

    @Column
    private char sign;

    @Column
    private DataViewPeriod period;

    @Column
    private GoalType goalType;

    @Column
    private CategoryType categoryType;

    @Column
    private int categoryId;
}

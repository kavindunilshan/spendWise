package com.app.spendWise.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goalId;

    @Column
    private String name;

    @Column(length = 30)
    private String user_id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    private double amount;

    @Column
    private String sign;

    @Column
    private DataViewPeriod period;

    @Column
    private GoalType type;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", insertable = false, updatable = false)
    private Category category;

    @Column
    private Integer category_id;
}

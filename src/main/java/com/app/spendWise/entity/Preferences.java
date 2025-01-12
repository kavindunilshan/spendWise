package com.app.spendWise.entity;

import com.app.spendWise.utils.DataViewPeriod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="`preferences`")
@Getter
@Setter
@ToString
public class Preferences {

    @Id
    @Column(length = 30)
    private String userId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;

    @Column()
    private Boolean isDarkMode;

    @Column()
    private Boolean isIncomePieChart;

    @Column()
    private Boolean isExpensePieChart;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private DataViewPeriod dataViewPeriod;
}

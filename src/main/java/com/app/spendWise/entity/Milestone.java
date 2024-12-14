package com.app.spendWise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="`milestone`")
@Getter @Setter
@ToString
public class Milestone {
    @Id
    @Column()
    private int milestoneId;

    @Column(length = 30)
    private String user_id;

    @Column
    private String name;

    @Column
    private int goalId;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;

}

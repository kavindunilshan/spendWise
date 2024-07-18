package com.app.spendWise.repository;

import com.app.spendWise.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    List<Goal> findByUserUserId(String userId);
}

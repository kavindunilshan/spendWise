package com.app.spendWise.service;

import com.app.spendWise.entity.Goal;
import com.app.spendWise.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public List<Goal> getGoalsByUserId(String userId) {
        return goalRepository.findByUserUserId(userId);
    }
}

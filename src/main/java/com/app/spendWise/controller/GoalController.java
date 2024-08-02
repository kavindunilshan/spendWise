package com.app.spendWise.controller;

import com.app.spendWise.entity.Goal;
import com.app.spendWise.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/private/goals", produces = MediaType.APPLICATION_JSON_VALUE)
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("")
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        Goal savedGoal = goalService.createGoal(goal);
        return new ResponseEntity<>(savedGoal, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Goal>> getGoalsByUserId(@PathVariable String userId) {
        List<Goal> goals = goalService.getGoalsByUserId(userId);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable int id, @RequestBody Goal goal) {
        Goal updatedGoal = goalService.updateGoal(id, goal);
        return new ResponseEntity<>(updatedGoal, HttpStatus.OK);
    }

}

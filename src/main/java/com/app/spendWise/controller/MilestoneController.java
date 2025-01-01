package com.app.spendWise.controller;

import com.app.spendWise.entity.Milestone;
import com.app.spendWise.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/private/milestones", produces = MediaType.APPLICATION_JSON_VALUE)
public class MilestoneController {
    @Autowired
    private MilestoneService milestoneService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Milestone>> getMilestones(@PathVariable String userId) {
        List<Milestone> milestones = milestoneService.getMilestones(userId);
        return new ResponseEntity<>(milestones, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Milestone> createMilestone(@RequestBody Milestone milestone) {
        Milestone savedMilestone = milestoneService.createMilestone(milestone);
        return new ResponseEntity<>(savedMilestone, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Milestone> updateMilestone(@PathVariable int id, @RequestBody Milestone milestone) {
        Milestone updatedMilestone = milestoneService.updateMilestone(id, milestone);
        return new ResponseEntity<>(updatedMilestone, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_delete:resources')")
    public ResponseEntity<Void> deleteMilestone(@PathVariable int id) {
        milestoneService.deleteMilestone(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

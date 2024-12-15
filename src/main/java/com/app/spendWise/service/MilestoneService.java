package com.app.spendWise.service;

import com.app.spendWise.entity.Milestone;
import com.app.spendWise.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService {
    @Autowired
    private MilestoneRepository milestoneRepository;

    public List<Milestone> getMilestones(String userId) {
        return milestoneRepository.findByUser_UserId(userId);
    }

    public Milestone createMilestone(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    public void deleteMilestone(int id) {
        milestoneRepository.deleteById(id);
    }

    public Milestone updateMilestone(int id, Milestone milestone) {
        milestone.setMilestoneId(id);
        milestoneRepository.save(milestone);
        return milestone;
    }

}

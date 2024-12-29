package com.app.spendWise.service;

import com.app.spendWise.entity.Milestone;
import com.app.spendWise.observer.TransactionEvent;
import com.app.spendWise.observer.TransactionEventType;
import com.app.spendWise.observer.TransactionNotifier;
import com.app.spendWise.observer.TransactionObserver;
import com.app.spendWise.repository.MilestoneRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService implements TransactionObserver {
    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private TransactionNotifier transactionNotifier;

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

    @PostConstruct
    public void registerObserver() {
        System.out.println("Registering observer");
        transactionNotifier.addObserver(TransactionEventType.TRANSACTION_ADDED, this);
    }

    @Override
    public void onTransactionEvent(TransactionEvent event) {
        System.out.println("Transaction event received");
        if (event.getEventType() == TransactionEventType.TRANSACTION_ADDED) {

        }
    }
}

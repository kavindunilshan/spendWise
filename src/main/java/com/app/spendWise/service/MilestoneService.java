package com.app.spendWise.service;

import com.app.spendWise.entity.Goal;
import com.app.spendWise.entity.Milestone;
import com.app.spendWise.entity.Transaction;
import com.app.spendWise.observer.TransactionEvent;
import com.app.spendWise.observer.TransactionEventType;
import com.app.spendWise.observer.TransactionNotifier;
import com.app.spendWise.observer.TransactionObserver;
import com.app.spendWise.repository.GoalRepository;
import com.app.spendWise.repository.MilestoneRepository;
import com.app.spendWise.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService implements TransactionObserver {
    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private GoalRepository goalRepository;

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
            System.out.println("Transaction added event received");

            checkAndUpdateMilestones(event.getTransaction());

        }
    }

    public void checkAndUpdateMilestones(Transaction transaction) {
        List<Goal> goals = goalRepository.findByUserUserId(transaction.getUser_id());

        for (Goal goal : goals) {
            double totalSpent = calculateTotalSpent(transaction.getUser_id(), goal);

            if (goal.getTransactionType().name().equals("EXPENSE")) {
                // separate logic
            } else {

                if (totalSpent >= goal.getAmount()) {
                    System.out.println("Goal satisfied: " + goal.getName());
                    Milestone milestone = new Milestone();
                    milestone.setGoalId(goal.getGoalId());
                    milestone.setUser_id(transaction.getUser_id());
                    milestone.setName(goal.getName());
                    milestone.setDate(transaction.getDate());

                    milestoneRepository.save(milestone);
                }
            }
        }
    }

    private double calculateTotalSpent(String userId, Goal goal) {
        switch (goal.getPeriod()) {
            case MONTHLY:
                return transactionRepository.sumTransactionsForMonth(userId, goal.getCategory_id(), goal.getTransactionType());
            case YEARLY:
                return transactionRepository.sumTransactionsForYear(userId, goal.getCategory_id(), goal.getTransactionType());
            case ALL:
            default:
                return transactionRepository.sumTransactionsForAll(userId, goal.getCategory_id(), goal.getTransactionType());
        }
    }
}

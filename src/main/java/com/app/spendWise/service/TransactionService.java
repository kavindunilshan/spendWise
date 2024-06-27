package com.app.spendWise.service;

import com.app.spendWise.entity.Transaction;
import com.app.spendWise.exception.NotFoundException;
import com.app.spendWise.repository.TransactionRepository;
import com.app.spendWise.utils.CategoryType;
import com.app.spendWise.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionById(String  userId) {
        return transactionRepository.findByUserUserId(userId);
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getLastFiveTransactions(String userId) {
        return transactionRepository.findLastFiveTransactionsByUserId(userId, PageRequest.of(0, 5));
    }

    public HashMap<String, Double> getPocketMoney(String userId) {
        // Hashmap
        HashMap<String, Double> pocketMoney = new HashMap<>();

        // Income
        double income = transactionRepository.sumAmountsByUserIdAndCategoryType(userId, CategoryType.INCOME).doubleValue();

        // Expenses
        double expenses = transactionRepository.sumAmountsByUserIdAndCategoryType(userId, CategoryType.EXPENSE).doubleValue();

        // Pocket Money
        double pocket = income - expenses;

        // Put values in hashmap
        pocketMoney.put("income", income);
        pocketMoney.put("expenses", expenses);
        pocketMoney.put("pocket", pocket);

        return pocketMoney;
    }

    public HashMap<String, HashMap<String, Double>> getMonthlyDataByUserIdAndCategoryType(String userId, int months) {
        HashMap<String, HashMap<String, Double>> monthlyData = new HashMap<>();
        for(CategoryType type: CategoryType.values()) {
            List<Object[]> results = transactionRepository.findMonthlySumsByUserUserIdAndCategoryType(userId, type, months);

            HashMap<String, Double> monthlyExpenses = CommonUtils.generateMonthMap();

            for (Object[] result : results) {
                Integer year = (Integer) result[0];
                Integer month = (Integer) result[1];
                // result[2] is a double value
                Double total = (Double) result[2];

                String yearMonthKey = String.format("%d-%02d", year, month);

                if (monthlyExpenses.containsKey(yearMonthKey)) {
                    monthlyExpenses.put(yearMonthKey, total);
                }
            }

            monthlyData.put(type.toString(), monthlyExpenses);
        }

        return monthlyData;
    }

    public Map<String, Double> getExpenseBreakdownByCategory(String userId, CategoryType type) {
        List<Transaction> transactions = transactionRepository.findByUserUserIdAndCategoryType(userId, type);

        Map<String, Double> expenseBreakdown = new HashMap<>();

        // Calculate sum of amounts per category
        for (Transaction transaction : transactions) {
            String categoryName = transaction.getCategory().getName();
            double amount = transaction.getAmount();
            expenseBreakdown.put(categoryName, expenseBreakdown.getOrDefault(categoryName, 0.0) + amount);
        }

        return expenseBreakdown;
    }

    public Transaction updateTransaction(int id, Transaction newTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        existingTransaction.setCategory(newTransaction.getCategory());
        existingTransaction.setCustomCategory(newTransaction.getCustomCategory());
        existingTransaction.setUser(newTransaction.getUser());
        existingTransaction.setTimestamp(newTransaction.getTimestamp());
        existingTransaction.setDescription(newTransaction.getDescription());

        return transactionRepository.save(existingTransaction);
    }

    public void deleteTransaction(int id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}

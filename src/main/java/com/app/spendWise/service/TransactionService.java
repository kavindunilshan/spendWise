package com.app.spendWise.service;

import com.app.spendWise.entity.Transaction;
import com.app.spendWise.exception.NotFoundException;
import com.app.spendWise.repository.TransactionRepository;
import com.app.spendWise.utils.CategoryType;
import com.app.spendWise.utils.CommonUtils;
import com.app.spendWise.utils.DataViewPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
        return transactionRepository.findByUserUserIdOrderByTimestampDesc(userId);
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getLastFiveTransactions(String userId) {
        return transactionRepository.findLastFiveTransactionsByUserId(userId, PageRequest.of(0, 5));
    }

    public List<Transaction> getTransactionsBetweenPeriod(String userId, CategoryType type, LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByUserUserIdAndCategoryTypeAndDateBetween(userId, type, start, end);
    }

    public List<Transaction> getTransactionsBetweenPeriod(String userId, LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByUserUserIdAndDateBetween(userId, start, end);
    }

    public HashMap<String, Double> getPocketByPeriod(String userId, DataViewPeriod period, String periodValue) {
        HashMap<String, Double> pocketMoney = new HashMap<>();

        double income = 0;
        double expenses = 0;
        double savings = 0;

        // DataViewPeriod enum
        if (period.equals(DataViewPeriod.ALL)) {
            income = transactionRepository.sumAmountsByUserIdAndCategoryType(userId, CategoryType.INCOME)
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0);
            expenses = transactionRepository.sumAmountsByUserIdAndCategoryType(userId, CategoryType.EXPENSE)
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0);
            savings = transactionRepository.sumAmountsByUserIdAndCategoryType(userId, CategoryType.SAVING)
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0);
        } else if (period.equals(DataViewPeriod.MONTHLY)) {
            YearMonth yearMonth = YearMonth.parse(periodValue, DateTimeFormatter.ofPattern("MM yyyy"));
            LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
            LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);

            income = transactionRepository.sumAmountsByUserIdCategoryTypeAndMonth(userId, CategoryType.INCOME, startOfMonth, endOfMonth)
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0);
            expenses = transactionRepository.sumAmountsByUserIdCategoryTypeAndMonth(userId, CategoryType.EXPENSE, startOfMonth, endOfMonth)
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0);
            savings = transactionRepository.sumAmountsByUserIdCategoryTypeAndMonth(userId, CategoryType.SAVING, startOfMonth, endOfMonth)
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0);

        } else if (period.equals(DataViewPeriod.YEARLY)) {
            int year = Integer.parseInt(periodValue);
            LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0, 0);
            LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59, 999999999);

            income = transactionRepository.sumAmountsByUserIdCategoryTypeAndMonth(userId, CategoryType.INCOME, startOfYear, endOfYear)
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0);
            expenses = transactionRepository.sumAmountsByUserIdCategoryTypeAndMonth(userId, CategoryType.EXPENSE, startOfYear, endOfYear)
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0);
            savings = transactionRepository.sumAmountsByUserIdCategoryTypeAndMonth(userId, CategoryType.SAVING, startOfYear, endOfYear)
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0);
        }

        double pocket = income - expenses;
        pocketMoney.put("income", income);
        pocketMoney.put("savings", savings);
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

    public  Map<String, Double> calculateBreakdown(List<Transaction> transactions) {
        Map<String, Double> expenseBreakdown = new HashMap<>();

        // Calculate sum of amounts per category
        for (Transaction transaction : transactions) {
            String categoryName = transaction.getCategory().getName();
            double amount = transaction.getAmount();
            expenseBreakdown.put(categoryName, expenseBreakdown.getOrDefault(categoryName, 0.0) + amount);
        }

        return expenseBreakdown;
    }

    public Map<String, Double> getExpenseBreakdownByCategory(String userId, CategoryType type) {
        List<Transaction> transactions = transactionRepository.findByUserUserIdAndCategoryType(userId, type);

        return calculateBreakdown(transactions);
    }

    public Map<String, Double> getExpenseBreakdownByCategoryAndMonth(String userId, CategoryType type, String month) {
        // Parse month string to YearMonth
        YearMonth yearMonth;

        LocalDateTime start = null;
        LocalDateTime end = null;

        if (month.length() == 4) {
            int year = Integer.parseInt(month);
            start = LocalDateTime.of(year, 1, 1, 0, 0, 0);
            end = LocalDateTime.of(year, 12, 31, 23, 59, 59, 999999999);
        } else {
            yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("MM yyyy"));
            start = yearMonth.atDay(1).atStartOfDay();
            end = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);
        }

        // Fetch transactions for the specified month
        List<Transaction> transactions = transactionRepository.findByUserUserIdAndCategoryTypeAndDateBetween(
                userId, type, start, end);

        return calculateBreakdown(transactions);
    }

    public Transaction updateTransaction(int id, Transaction newTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        if (newTransaction.getCategory() != null) {
            existingTransaction.setCategory(newTransaction.getCategory());
        }

        if (newTransaction.getCustomCategory() != null) {
            existingTransaction.setCustomCategory(newTransaction.getCustomCategory());
        }

        if (newTransaction.getDate() != null) {
            existingTransaction.setDate(newTransaction.getDate());
        }

        if (newTransaction.getTimestamp() != null) {
            existingTransaction.setTimestamp(newTransaction.getTimestamp());
        }

        if (newTransaction.getDescription() != null) {
            existingTransaction.setDescription(newTransaction.getDescription());
        }

        if (newTransaction.getAmount() != 0) {
            existingTransaction.setAmount(newTransaction.getAmount());
        }

        return transactionRepository.save(existingTransaction);
    }

    public void deleteTransaction(int id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}

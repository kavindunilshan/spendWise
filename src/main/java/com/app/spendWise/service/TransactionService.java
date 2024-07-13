package com.app.spendWise.service;

import com.app.spendWise.entity.Transaction;
import com.app.spendWise.exception.NotFoundException;
import com.app.spendWise.repository.TransactionRepository;
import com.app.spendWise.utils.CategoryType;
import com.app.spendWise.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public HashMap<String, Double> getPocketMoneyForMonth(String userId, String month) {
        // Parse month string to YearMonth
        YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("MM yyyy"));

        // Calculate start and end of month LocalDateTime
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);

        // Hashmap
        HashMap<String, Double> pocketMoney = new HashMap<>();

        System.out.println("Start of month: " + startOfMonth + " End of month: " + endOfMonth);

        // Income
        double income = transactionRepository.sumAmountsByUserIdCategoryTypeAndMonth(userId, CategoryType.INCOME, startOfMonth, endOfMonth).doubleValue();

        // Expenses
        double expenses = transactionRepository.sumAmountsByUserIdCategoryTypeAndMonth(userId, CategoryType.EXPENSE, startOfMonth, endOfMonth).doubleValue();

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
        YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("MM yyyy"));

        // Calculate start and end of month LocalDateTime
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);

        // Fetch transactions for the specified month
        List<Transaction> transactions = transactionRepository.findByUserUserIdAndCategoryTypeAndDateBetween(
                userId, type, startOfMonth, endOfMonth);

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

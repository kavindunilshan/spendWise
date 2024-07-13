package com.app.spendWise.controller;

import com.app.spendWise.entity.Transaction;
import com.app.spendWise.service.TransactionService;
import com.app.spendWise.utils.CategoryType;
import com.app.spendWise.utils.DataViewPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/private/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionById(@PathVariable String  userId) {
        List<Transaction> transaction = transactionService.getTransactionById(userId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{userId}/last-five")
    public ResponseEntity<List<Transaction>> getLastFiveTransactions(@PathVariable String userId) {
        List<Transaction> transactions = transactionService.getLastFiveTransactions(userId);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/pocket/{userId}/{period}/{value}")
    public ResponseEntity<HashMap<String,Double>> getPocketMoneyMonthly(@PathVariable String userId,
                                                                        @PathVariable DataViewPeriod period,
                                                                        @PathVariable String value) {
        return ResponseEntity.ok(transactionService.getPocketByPeriod(userId, period, value));
    }

    @GetMapping("/{userId}/{type}/breakdown")
    public Map<String, Double> getExpenseBreakdownByCategory(
            @PathVariable String userId,
            @PathVariable CategoryType type,
            @RequestParam(name = "isMonthly", required = false, defaultValue = "false") boolean isMonthly,
            @RequestParam(name = "month", required = false) String month) {

        if (isMonthly && (!month.isEmpty())) {
            return transactionService.getExpenseBreakdownByCategoryAndMonth(userId, type, month);
        } else {
            return transactionService.getExpenseBreakdownByCategory(userId, type);
        }
    }


    @GetMapping("/{userId}/monthly/{months}")
    public ResponseEntity<HashMap<String, HashMap<String, Double>>> getMonthlyExpensesByUserIdAndCategoryType(@PathVariable String userId,
                                                                                             @PathVariable int months) {
        return ResponseEntity.ok(transactionService.getMonthlyDataByUserIdAndCategoryType(userId, months));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable int id, @RequestBody Transaction transaction) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
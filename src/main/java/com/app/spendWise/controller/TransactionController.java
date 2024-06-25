package com.app.spendWise.controller;

import com.app.spendWise.entity.Transaction;
import com.app.spendWise.service.TransactionService;
import com.app.spendWise.utils.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{user_id}/last-five")
    public ResponseEntity<List<Transaction>> getLastFiveTransactions(@PathVariable String user_id) {
        List<Transaction> transactions = transactionService.getLastFiveTransactions(user_id);
        System.out.println("here" + transactions);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{type}/breakdown")
    public Map<String, Double> getExpenseBreakdownByCategory(@PathVariable String userId, @PathVariable CategoryType type) {
        return transactionService.getExpenseBreakdownByCategory(userId, type);
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
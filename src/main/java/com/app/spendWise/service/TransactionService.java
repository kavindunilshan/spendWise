package com.app.spendWise.service;

import com.app.spendWise.entity.Transaction;
import com.app.spendWise.exception.NotFoundException;
import com.app.spendWise.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
    }

    public Transaction updateTransaction(int id, Transaction newTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        existingTransaction.setCategory(newTransaction.getCategory());
        existingTransaction.setCustomCategory(newTransaction.getCustomCategory());
        existingTransaction.setUser(newTransaction.getUser());
        existingTransaction.setCategoryType(newTransaction.getCategoryType());
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

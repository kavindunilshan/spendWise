package com.app.spendWise.repository;

import com.app.spendWise.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT t FROM Transaction t ORDER BY t.transactionId DESC")
    List<Transaction> findLastFiveTransactions(Pageable pageable);

}

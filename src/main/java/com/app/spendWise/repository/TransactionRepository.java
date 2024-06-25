package com.app.spendWise.repository;

import com.app.spendWise.entity.Transaction;
import com.app.spendWise.utils.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT t FROM Transaction t WHERE t.user.userId = :userId ORDER BY t.transactionId DESC")
    List<Transaction> findLastFiveTransactionsByUserId(@Param("userId") String userId, Pageable pageable);

    List<Transaction> findByUserUserIdAndCategoryType(String user_userId, CategoryType category_type);

}

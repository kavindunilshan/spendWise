package com.app.spendWise.repository;

import com.app.spendWise.entity.Transaction;
import com.app.spendWise.utils.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    // find user by user id
    List<Transaction> findByUserUserIdOrderByTimestampDesc(String userId);

    @Query(value = "SELECT t FROM Transaction t WHERE t.user.userId = :userId ORDER BY t.transactionId DESC")
    List<Transaction> findLastFiveTransactionsByUserId(@Param("userId") String userId, Pageable pageable);

    List<Transaction> findByUserUserIdAndCategoryType(String user_userId, CategoryType category_type);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.userId = :userId AND t.category.type = :categoryType")
    Optional<BigDecimal> sumAmountsByUserIdAndCategoryType(@Param("userId") String userId, @Param("categoryType") CategoryType categoryType);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.userId = :userId AND t.category.type = :categoryType AND t.date >= :startDate AND t.date < :endDate")
    Optional<BigDecimal> sumAmountsByUserIdCategoryTypeAndMonth(@Param("userId") String userId, @Param("categoryType") CategoryType categoryType, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT EXTRACT(YEAR FROM t.date) as year, EXTRACT(MONTH FROM t.date) as month, SUM(t.amount) as total " +
            "FROM Transaction t WHERE t.user.userId = :userId AND t.category.type = :categoryType " +
            "GROUP BY EXTRACT(YEAR FROM t.date), EXTRACT(MONTH FROM t.date) " +
            "ORDER BY year DESC, month DESC " +
            "LIMIT :months")
    List<Object[]> findMonthlySumsByUserUserIdAndCategoryType(@Param("userId") String userId,
                                                              @Param("categoryType") CategoryType categoryType,
                                                              @Param("months") int months);

    List<Transaction> findByUserUserIdAndCategoryTypeAndDateBetween(
            String userId, CategoryType categoryType, LocalDateTime startDate, LocalDateTime endDate);

    List<Transaction> findByUserUserIdAndDateBetween(
            String userId, LocalDateTime startDate, LocalDateTime endDate);




    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.userId = :userId AND t.category.categoryId = :categoryId AND t.category.type = :type AND MONTH(t.date) = MONTH(CURRENT_DATE) AND YEAR(t.date) = YEAR(CURRENT_DATE)")
    double sumTransactionsForMonth(@Param("userId") String userId, @Param("categoryId") Integer categoryId, @Param("type") CategoryType type);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.userId = :userId AND t.category.categoryId = :categoryId AND t.category.type = :type AND YEAR(t.date) = YEAR(CURRENT_DATE)")
    double sumTransactionsForYear(@Param("userId") String userId, @Param("categoryId") Integer categoryId, @Param("type") CategoryType type);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.userId = :userId AND t.category.categoryId = :categoryId AND t.category.type = :type")
    double sumTransactionsForAll(@Param("userId") String userId, @Param("categoryId") Integer categoryId, @Param("type") CategoryType type);


}

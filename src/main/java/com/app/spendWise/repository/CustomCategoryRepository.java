package com.app.spendWise.repository;

import com.app.spendWise.entity.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Integer> {
    @Query("SELECT c FROM CustomCategory c WHERE c.user.userId = ?1")
    Optional<List<CustomCategory>> findAllByUserId(String userId);
}

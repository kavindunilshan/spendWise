package com.app.spendWise.repository;

import com.app.spendWise.entity.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Integer> {
    List<Advice> findByUserId(String userId);
}

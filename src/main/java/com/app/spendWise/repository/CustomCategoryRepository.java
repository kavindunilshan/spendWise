package com.app.spendWise.repository;

import com.app.spendWise.entity.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Integer> {
}

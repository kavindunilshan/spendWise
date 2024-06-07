package com.app.spendWise.repository;

import com.app.spendWise.entity.Category;
import com.app.spendWise.utils.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByType(CategoryType type);
}

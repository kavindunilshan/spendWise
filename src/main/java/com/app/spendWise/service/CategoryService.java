package com.app.spendWise.service;

import com.app.spendWise.entity.Category;
import com.app.spendWise.exception.NotFoundException;
import com.app.spendWise.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    public Category updateCategory(int id, Category newCategory) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        existingCategory.setName(newCategory.getName());
        existingCategory.setType(newCategory.getType());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}

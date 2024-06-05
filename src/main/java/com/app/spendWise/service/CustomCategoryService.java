package com.app.spendWise.service;

import com.app.spendWise.entity.CustomCategory;
import com.app.spendWise.exception.NotFoundException;
import com.app.spendWise.repository.CustomCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomCategoryService {

    @Autowired
    private CustomCategoryRepository customCategoryRepository;

    public CustomCategory createCustomCategory(CustomCategory customCategory) {
        return customCategoryRepository.save(customCategory);
    }

    public CustomCategory getCustomCategoryById(int id) {
        return customCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CustomCategory not found"));
    }

    public CustomCategory updateCustomCategory(int id, CustomCategory newCustomCategory) {
        CustomCategory existingCustomCategory = customCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CustomCategory not found"));

        existingCustomCategory.setUser(newCustomCategory.getUser());
        existingCustomCategory.setName(newCustomCategory.getName());
        existingCustomCategory.setType(newCustomCategory.getType());

        return customCategoryRepository.save(existingCustomCategory);
    }

    public void deleteCustomCategory(int id) {
        customCategoryRepository.deleteById(id);
    }
}

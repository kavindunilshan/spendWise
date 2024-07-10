package com.app.spendWise.controller;

import com.app.spendWise.entity.CustomCategory;
import com.app.spendWise.service.CustomCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/private/custom-categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomCategoryController {

    @Autowired
    private CustomCategoryService customCategoryService;

    @PostMapping("/")
    public ResponseEntity<CustomCategory> createCustomCategory(@RequestBody CustomCategory customCategory) {
        CustomCategory savedCustomCategory = customCategoryService.createCustomCategory(customCategory);
        return new ResponseEntity<>(savedCustomCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CustomCategory>> getCustomCategoryByUserId(@PathVariable String id) {
        List<CustomCategory> customCategory = customCategoryService.getCustomCategoryById(id);
        return new ResponseEntity<>(customCategory, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomCategory> updateCustomCategory(@PathVariable int id, @RequestBody CustomCategory customCategory) {
        CustomCategory updatedCustomCategory = customCategoryService.updateCustomCategory(id, customCategory);
        return new ResponseEntity<>(updatedCustomCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomCategory(@PathVariable int id) {
        customCategoryService.deleteCustomCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

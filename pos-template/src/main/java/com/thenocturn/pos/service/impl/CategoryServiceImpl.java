package com.thenocturn.pos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thenocturn.pos.entity.Category;
import com.thenocturn.pos.repository.CategoryRepository;
import com.thenocturn.pos.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existing = this.getCategoryById(id);

        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        existing.setIsActive(category.getIsActive());

        return categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        category.setIsActive(false);
        categoryRepository.save(category);
    }
}
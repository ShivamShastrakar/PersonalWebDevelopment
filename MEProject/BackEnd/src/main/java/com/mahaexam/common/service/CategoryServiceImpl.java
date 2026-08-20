package com.mahaexam.common.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Category;
import com.mahaexam.common.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category create(Category category) {
    	  if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
              throw new ValidationException("Category name already exists.");
          }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
    	 if (categoryRepository.existsByCategoryNameAndExceptId(category.getCategoryName(), category.getId())) {
             throw new ValidationException("Category name already exists.");
         }
        return categoryRepository.update(category);
    }

    @Override
    public void delete(int id) {
        categoryRepository.delete(id);
    }
}

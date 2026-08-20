package com.mahaexam.common.service;

import com.mahaexam.common.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(int id);

    Category create(Category category);

    Category update(Category category);

    void delete(int id);
}

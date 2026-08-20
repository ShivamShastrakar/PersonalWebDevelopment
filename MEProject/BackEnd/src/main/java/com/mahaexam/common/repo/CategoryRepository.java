package com.mahaexam.common.repo;

import com.mahaexam.common.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();

    Optional<Category> findById(int id);

    Category save(Category category);

    Category update(Category category);

    void delete(int id); // Soft delete

	boolean existsByCategoryName(String categoryName);

	boolean existsByCategoryNameAndExceptId(String categoryName,  int id);
}

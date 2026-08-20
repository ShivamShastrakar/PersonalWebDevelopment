package com.mahaexam.common.controller;

import com.mahaexam.common.bean.CategoryBean;
import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Board;
import com.mahaexam.common.model.Category;
import com.mahaexam.common.model.Chapter;
import com.mahaexam.common.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController  extends BaseController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable int id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SuccessResponseBean> create(@RequestBody CategoryBean bean) {
    	UserBean user = getUser();
    	Category categroy = new Category();
    	categroy.setCategoryName(bean.getCategoryName());
    	categroy.setId(bean.getId());
        categoryService.create(categroy);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponseBean.builder().status("success").message(
                        "Board name registered successfully").build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> update(@PathVariable int id, @RequestBody CategoryBean bean) {
    	Category category = new Category();
    	category.setId(id);
    	category.setCategoryName(bean.getCategoryName());
    	category.setDeleted(bean.isDeleted());
    	categoryService.update(category);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponseBean.builder().status("success").message(
                        "Board name updated successfully").build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}


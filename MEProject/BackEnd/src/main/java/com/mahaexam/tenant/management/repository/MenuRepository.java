package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Menu;

public interface MenuRepository {
    List<Menu> findMenusByUserId(Long userId, Long roleId);

    List<Menu> findAll();

    Optional<Menu> findById(Integer menuId);

    Integer save(Menu menu);  // returns generated ID

    int update(Menu menu);    // returns row count

    int deleteById(Integer menuId);
}
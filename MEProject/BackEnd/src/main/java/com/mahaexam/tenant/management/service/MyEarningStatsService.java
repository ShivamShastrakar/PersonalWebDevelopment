package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.MyEarningStats;

import java.util.List;
import java.util.Optional;

public interface MyEarningStatsService {
    MyEarningStats save(MyEarningStats myEarningStats);

    Optional<MyEarningStats> findById(Long id);

    List<MyEarningStats> findByUserId(Long userId);

    MyEarningStats update(MyEarningStats myEarningStats);

    void delete(Long id);

    List<MyEarningStats> findAll();

    boolean existsById(Long id);
}

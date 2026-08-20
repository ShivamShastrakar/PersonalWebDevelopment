package com.mahaexam.common.repo;

import com.mahaexam.common.model.AreaOfInterest;

import java.util.List;
import java.util.Optional;

public interface AreaOfInterestRepository {
    AreaOfInterest save(AreaOfInterest areaOfInterest);
    Optional<AreaOfInterest> findById(Integer id);
    List<AreaOfInterest> findAll();
    void update(AreaOfInterest areaOfInterest);
    void deleteById(Integer id);
}

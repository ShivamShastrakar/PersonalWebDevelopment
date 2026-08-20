package com.mahaexam.common.service;

import com.mahaexam.common.model.AreaOfInterest;

import java.util.List;
import java.util.Optional;

public interface AreaOfInterestService {
    AreaOfInterest create(AreaOfInterest areaOfInterest);
    Optional<AreaOfInterest> getById(Integer id);
    List<AreaOfInterest> getAll();
    AreaOfInterest update(Integer id, AreaOfInterest areaOfInterest);
    void delete(Integer id);
}

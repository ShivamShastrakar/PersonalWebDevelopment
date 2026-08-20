package com.mahaexam.common.service;

import com.mahaexam.common.model.AreaOfInterest;
import com.mahaexam.common.repo.AreaOfInterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaOfInterestServiceImpl implements AreaOfInterestService {

    private final AreaOfInterestRepository   repository;

    @Autowired
    public AreaOfInterestServiceImpl(AreaOfInterestRepository repository) {
        this.repository = repository;
    }

    @Override
    public AreaOfInterest create(AreaOfInterest areaOfInterest) {
        return repository.save(areaOfInterest);
    }

    @Override
    public Optional<AreaOfInterest> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<AreaOfInterest> getAll() {
        return repository.findAll();
    }

    @Override
    public AreaOfInterest update(Integer id, AreaOfInterest updatedArea) {
        Optional<AreaOfInterest> existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("AreaOfInterest not found with id: " + id);
        }
        AreaOfInterest area = existing.get();
        area.setName(updatedArea.getName());
        repository.update(area);
        return area;
    }

    @Override
    public void delete(Integer id) {
        Optional<AreaOfInterest> existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("AreaOfInterest not found with id: " + id);
        }
        repository.deleteById(id);
    }
}

package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.Zone;

public interface ZoneRepository {
    Zone save(Zone zone);
    Optional<Zone> findById(int id);
    List<Zone> findAll();
    List<Zone> findByTenant(Long tenantId);
    int update(Zone zone);
    int deleteById(int id);
}

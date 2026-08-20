package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.Config;

public interface ConfigRepository {
    Config save(Config config);
    Optional<Config> findByName(String name);
    List<Config> findAllActive();
    List<Config> findAllDeleted();
    Config update(Config config);
    void softDelete(String name);
}
package com.mahaexam.common.service;

import com.mahaexam.common.model.Config;
import com.mahaexam.common.repo.ConfigRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigServiceImpl implements ConfigService {
    private final ConfigRepository repository;

    public ConfigServiceImpl(ConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    @CachePut(value = "configCache", key = "#config.name")
    public Config save(Config config) {
        validateConfig(true, config);
        if (config.getCreatedAt() == null) {
            config.setCreatedAt(LocalDateTime.now());
        }
        if (config.getDeleted() == null) {
            config.setDeleted("0");
        }
        return repository.save(config);
    }

    @Override
    @Cacheable(value = "configCache", key = "#name")
    public Optional<Config> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Configuration name is required");
        }
        return repository.findByName(name);
    }

    @Override
    public Boolean getBinaryBooleanConfig(String name) {
        Optional<Config> configOpt = findByName(name); // Reuses cache
        return configOpt.map(Config::getValue)
                .map(value -> {
                    if ("1".equals(value)) {
                        return true;
                    } else if ("0".equals(value)) {
                        return false;
                    } else {
                        throw new IllegalArgumentException("Invalid binary boolean value for " + name + ": " + value);
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("Configuration " + name + " is missing"));
    }

    @Override
    @Cacheable(value = "configCache", key = "'activeConfigs'")
    public List<Config> findAllActive() {
        return repository.findAllActive();
    }

    @Override
    @Cacheable(value = "configCache", key = "'deletedConfigs'")
    public List<Config> findAllDeleted() {
        return repository.findAllDeleted();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    @CachePut(value = "configCache", key = "#config.name")
    public Config update(Config config) {
        validateConfig(false, config);
        if (repository.findByName(config.getName()).isEmpty()) {
            throw new IllegalArgumentException("Configuration with name " + config.getName() + " does not exist");
        }
        config.setUpdatedAt(LocalDateTime.now());
        return repository.update(config);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    @CacheEvict(value = "configCache", key = "#name")
    public void softDelete(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Configuration name is required");
        }
        if (repository.findByName(name).isEmpty()) {
            throw new IllegalArgumentException("Configuration with name " + name + " does not exist");
        }
        repository.softDelete(name);
    }

    private void validateConfig(boolean isCreate, Config config) {
        if (config == null) {
            throw new IllegalArgumentException("Configuration cannot be null");
        }
        if (config.getName() == null || config.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Configuration name is required");
        }
        if (config.getValue() == null || config.getValue().trim().isEmpty()) {
            throw new IllegalArgumentException("Configuration value is required");
        }

        if (isCreate && findByName(config.getName()).isPresent()) {
            throw new IllegalArgumentException("Configuration is already exist with this name.");
        }
    }
}
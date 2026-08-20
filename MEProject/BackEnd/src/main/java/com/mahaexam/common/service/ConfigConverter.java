package com.mahaexam.common.service;

import org.springframework.stereotype.Component;

import com.mahaexam.common.bean.ConfigBean;
import com.mahaexam.common.model.Config;

@Component
public class ConfigConverter {

    public Config toEntity(ConfigBean bean) {
        if (bean == null) {
            return null;
        }
        Config config = new Config();
        config.setName(bean.getName());
        config.setValue(bean.getValue());
        config.setCreatedAt(bean.getCreatedAt());
        config.setUpdatedAt(bean.getUpdatedAt());
        config.setDeletedAt(bean.getDeletedAt());
        config.setDeleted(bean.getDeleted());
        return config;
    }

    public ConfigBean toBean(Config config) {
        if (config == null) {
            return null;
        }
        return new ConfigBean(
                config.getName(),
                config.getValue(),
                config.getCreatedAt(),
                config.getUpdatedAt(),
                config.getDeletedAt(),
                config.getDeleted()
        );
    }
}


package com.mahaexam.common.controller;

import com.mahaexam.common.bean.ConfigBean;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.service.ConfigConverter;
import com.mahaexam.common.service.ConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/configs")
public class ConfigController {

    private final ConfigService configService;
    private final ConfigConverter configConverter;

    public ConfigController(ConfigService configService, ConfigConverter configConverter) {
        this.configService = configService;
        this.configConverter = configConverter;
    }

    @PostMapping
    public ResponseEntity<ConfigBean> createConfig(@RequestBody ConfigBean configBean) {
        Config config = configConverter.toEntity(configBean);
        Config savedConfig = configService.save(config);
        ConfigBean savedBean = configConverter.toBean(savedConfig);
        return new ResponseEntity<>(savedBean, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ConfigBean> getConfigByName(@PathVariable String name) {
        Optional<Config> configOpt = configService.findByName(name);
        return configOpt.map(config -> {
            ConfigBean bean = configConverter.toBean(config);
            return new ResponseEntity<>(bean, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/binary/{name}")
    public ResponseEntity<Boolean> getBinaryBooleanConfig(@PathVariable String name) {
        Boolean value = configService.getBinaryBooleanConfig(name);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ConfigBean>> getAllActiveConfigs() {
        List<Config> activeConfigs = configService.findAllActive();
        List<ConfigBean> activeBeans = activeConfigs.stream()
                .map(configConverter::toBean)
                .collect(Collectors.toList());
        return new ResponseEntity<>(activeBeans, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<ConfigBean>> getAllDeletedConfigs() {
        List<Config> deletedConfigs = configService.findAllDeleted();
        List<ConfigBean> deletedBeans = deletedConfigs.stream()
                .map(configConverter::toBean)
                .collect(Collectors.toList());
        return new ResponseEntity<>(deletedBeans, HttpStatus.OK);
    }

    @PutMapping("/{name}")
    public ResponseEntity<ConfigBean> updateConfig(@PathVariable String name, @RequestBody ConfigBean configBean) {
        // Ensure the name in the path matches the config's name for consistency
        if (!name.equals(configBean.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Config config = configConverter.toEntity(configBean);
        Config updatedConfig = configService.update(config);
        ConfigBean updatedBean = configConverter.toBean(updatedConfig);
        return new ResponseEntity<>(updatedBean, HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> softDeleteConfig(@PathVariable String name) {
        configService.softDelete(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

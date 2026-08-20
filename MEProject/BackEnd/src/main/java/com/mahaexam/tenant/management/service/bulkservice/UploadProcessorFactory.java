package com.mahaexam.tenant.management.service.bulkservice;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UploadProcessorFactory {

    private final Map<String, UploadProcessor<?>> processorMap;

    public UploadProcessorFactory(List<UploadProcessor<?>> processors) {
        this.processorMap = processors.stream()
                .collect(Collectors.toMap(p -> p.getSupportedClass().toLowerCase(), p -> p));
    }

    public UploadProcessor<?> getProcessor(String entityType) {
        return processorMap.get(entityType.toLowerCase());
    }
}

package com.mahaexam.tenant.management.service.bulkservice;

import java.util.List;

public class ValidationResult<T> {
    private List<T> validEntities;
    private List<T> invalidEntities;

    public List<T> getValidEntities() { return validEntities; }
    public void setValidEntities(List<T> validEntities) { this.validEntities = validEntities; }
    public List<T> getInvalidEntities() { return invalidEntities; }
    public void setInvalidEntities(List<T> invalidEntities) { this.invalidEntities = invalidEntities; }
}

package com.mahaexam.packagemanagment.repository;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.util.RepoUtil;
import com.mahaexam.packagemanagment.model.ServiceModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceMapper implements RowMapper<ServiceModel> {
    @Override
    public ServiceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceModel service = new ServiceModel();
        service.setId(rs.getInt("id"));
        service.setServiceName(rs.getString("service_name"));
        service.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        service.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        service.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        service.setDeleted(rs.getString("deleted"));
        service.setUpdatedBy(rs.getObject("updated_by", Integer.class));
        service.setServiceDetails(rs.getString("service_details"));
        service.setServiceType(rs.getString("service_type"));
        service.setOptions(rs.getString("options"));
        service.setTenantId(rs.getObject("tenant_id", Long.class));
        service.setPackageId(RepoUtil.getOptionalInteger(rs, "package_id"));
        return service;
    }
}
package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.Address;

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setAddressId(rs.getLong("address_id"));
        address.setUserId(rs.getLong("user_id"));
        address.setAddressText(rs.getString("address_text"));
        address.setStateId(rs.getObject("state_id") != null ? rs.getInt("state_id") : null);
        address.setDistrictId(rs.getObject("district_id") != null ? rs.getInt("district_id") : null);
        address.setTalukaId(rs.getObject("taluka_id") != null ? rs.getInt("taluka_id") : null);
        address.setPlace(rs.getString("place"));
        address.setPincode(rs.getString("pincode"));

        address.setState(RepoUtil.getOptionalString(rs,"state_name"));
        address.setDistrict(RepoUtil.getOptionalString(rs,"district_name"));
        address.setTaluka(RepoUtil.getOptionalString(rs,"taluka_name"));
        return address;
    }
}
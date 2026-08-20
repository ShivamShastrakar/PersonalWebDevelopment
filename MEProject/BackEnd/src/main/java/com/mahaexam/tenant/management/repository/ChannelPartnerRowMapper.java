package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.ChannelPartner;

public class ChannelPartnerRowMapper implements RowMapper<ChannelPartner> {
    @Override
    public ChannelPartner mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChannelPartner channelPartner = new ChannelPartner();
        channelPartner.setPartnerId(rs.getLong("partner_id"));
        channelPartner.setUserId(rs.getLong("user_id"));
        channelPartner.setCompanyName(rs.getString("company_name"));
        channelPartner.setBusinessType(rs.getString("business_type"));
        channelPartner.setPanNumber(rs.getString("pan_number"));
        channelPartner.setTanNumber(rs.getString("tan_number"));
        channelPartner.setGstNumber(rs.getString("gst_number"));
        channelPartner.setBusinessExpYears(rs.getObject("business_exp_years") != null ? 
                                          rs.getInt("business_exp_years") : null);
        channelPartner.setServiceType(rs.getString("service_type"));
        channelPartner.setDeeperAssociationYears(rs.getObject("deeper_association_years") != null ? 
                                                rs.getInt("deeper_association_years") : null);
        channelPartner.setParentPartnerId(rs.getObject("parent_partner_id") != null ? 
                                         rs.getLong("parent_partner_id") : null);
        return channelPartner;
    }
}
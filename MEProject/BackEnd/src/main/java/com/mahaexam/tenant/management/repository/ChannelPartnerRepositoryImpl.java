package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.ChannelPartner;

@Repository
public class ChannelPartnerRepositoryImpl implements ChannelPartnerRepository {
	private final JdbcTemplate jdbcTemplate;

	public ChannelPartnerRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public ChannelPartner save(ChannelPartner channelPartner) {
		String sql = "INSERT INTO channel_partner (user_id, company_name, business_type, pan_number, tan_number, "
		           + "gst_number, business_exp_years, service_type, deeper_association_years, parent_partner_id) "
		           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    ps.setLong(1, channelPartner.getUserId());
		    ps.setString(2, channelPartner.getCompanyName());
		    ps.setString(3, channelPartner.getBusinessType());
		    ps.setString(4, channelPartner.getPanNumber());
		    ps.setString(5, channelPartner.getTanNumber());
		    ps.setString(6, channelPartner.getGstNumber());
		    ps.setInt(7, channelPartner.getBusinessExpYears());
		    ps.setString(8, channelPartner.getServiceType());
		    ps.setInt(9, channelPartner.getDeeperAssociationYears());
		    
		    // Use setObject to safely handle nullable parent_partner_id
		    if (channelPartner.getParentPartnerId() != null) {
		        ps.setLong(10, channelPartner.getParentPartnerId());
		    } else {
		        ps.setNull(10, Types.BIGINT);
		    }

		    return ps;
		}, keyHolder);

		// Retrieve and set the generated ID
		Number key = keyHolder.getKey();
		if (key != null) {
		    channelPartner.setPartnerId(key.longValue());
		}

		return channelPartner;

	}

	@Override
	public Optional<ChannelPartner> findById(Long partnerId) {
		String sql = "SELECT * FROM channel_partner WHERE partner_id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ChannelPartnerRowMapper(), partnerId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<ChannelPartner> findAll() {
		String sql = "SELECT * FROM channel_partner";
		return jdbcTemplate.query(sql, new ChannelPartnerRowMapper());
	}

	@Override
	public ChannelPartner update(ChannelPartner channelPartner) {
		String sql = "UPDATE channel_partner SET user_id = ?, company_name = ?, business_type = ?, pan_number = ?, "
				+ "tan_number = ?, gst_number = ?, business_exp_years = ?, service_type = ?, "
				+ "deeper_association_years = ?, parent_partner_id = ? WHERE partner_id = ?";

		jdbcTemplate.update(sql, channelPartner.getUserId(), channelPartner.getCompanyName(),
				channelPartner.getBusinessType(), channelPartner.getPanNumber(), channelPartner.getTanNumber(),
				channelPartner.getGstNumber(), channelPartner.getBusinessExpYears(), channelPartner.getServiceType(),
				channelPartner.getDeeperAssociationYears(), channelPartner.getParentPartnerId(),
				channelPartner.getPartnerId());
		return channelPartner;
	}

	@Override
	public void delete(Long partnerId) {
		String sql = "DELETE FROM channel_partner WHERE partner_id = ?";
		jdbcTemplate.update(sql, partnerId);
	}

	@Override
	public Optional<ChannelPartner> findByUserId(Long userId) {
		String sql = "SELECT * FROM channel_partner WHERE user_id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ChannelPartnerRowMapper(), userId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public ChannelPartner registerCP(ChannelPartner channelPartner) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO channel_partner (user_id, company_name, business_type, pan_number, tan_number, "
		           + "gst_number, business_exp_years, service_type, deeper_association_years, parent_partner_id) "
		           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    ps.setLong(1, channelPartner.getUserId());
		    ps.setString(2, channelPartner.getCompanyName());
		    ps.setString(3, channelPartner.getBusinessType());
		    ps.setString(4, channelPartner.getPanNumber());
		    ps.setString(5, channelPartner.getTanNumber());
		    ps.setString(6, channelPartner.getGstNumber());
		    ps.setInt(7, channelPartner.getBusinessExpYears());
		    ps.setString(8, channelPartner.getServiceType());
		    ps.setInt(9, channelPartner.getDeeperAssociationYears());
		    
		    // Use setObject to safely handle nullable parent_partner_id
		    if (channelPartner.getParentPartnerId() != null) {
		        ps.setLong(10, channelPartner.getParentPartnerId());
		    } else {
		        ps.setNull(10, Types.BIGINT);
		    }

		    return ps;
		}, keyHolder);

		// Retrieve and set the generated ID
		Number key = keyHolder.getKey();
		if (key != null) {
		    channelPartner.setPartnerId(key.longValue());
		}

		return channelPartner;

	}
	
	
}
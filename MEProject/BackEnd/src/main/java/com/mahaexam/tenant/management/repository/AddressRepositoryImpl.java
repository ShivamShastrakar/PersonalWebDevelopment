package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.Address;

@Repository
public class AddressRepositoryImpl implements AddressRepository {
	private static final Logger logger = LoggerFactory.getLogger(AddressRepositoryImpl.class);
	private final JdbcTemplate jdbcTemplate;
    private static final int BATCH_SIZE = 2000;
	public AddressRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Address save(Address address) {
		String sql = "INSERT INTO address (user_id, address_text, state_id, district_id, taluka_id, place, pincode) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, address.getUserId());
			ps.setString(2, address.getAddressText());
			ps.setInt(3, address.getStateId());
			if (address.getDistrictId() != null) {
				ps.setInt(4, address.getDistrictId());
			} else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
			if (address.getTalukaId() != null) {
				ps.setInt(5, address.getTalukaId());
			} else {
				ps.setNull(5, java.sql.Types.INTEGER);
			}
			ps.setString(6, address.getPlace());
			ps.setString(7, address.getPincode());
			return ps;
		}, keyHolder);

		// Set the generated ID
		Number key = keyHolder.getKey();
		if (key != null) {
			address.setAddressId(key.longValue());
		}

		return address;

	}

	@Override
	public Optional<Address> findById(Long addressId) {
		String sql = """
                SELECT a.*, s.state_name,d.district_name ,t.taluka_name   FROM address a
                        inner join state s on a.state_id = s.id
                        left join district d  on a.district_id =d.id
                        left join taluka t on a.taluka_id = t.id 
                        WHERE address_id = ?
                """;
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new AddressRowMapper(), addressId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Address> findAll() {
		String sql = "SELECT * FROM address";
		return jdbcTemplate.query(sql, new AddressRowMapper());
	}

	@Override
	public Address update(Address address) {
		String sql = "UPDATE address SET  address_text = ?, state_id = ?, district_id = ?, "
				+ "taluka_id = ?, place = ?, pincode = ? WHERE address_id = ?";

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, address.getAddressText());
			ps.setInt(2, address.getStateId());
			if (address.getDistrictId() != null) {
				ps.setInt(3, address.getDistrictId());
			} else {
				ps.setNull(3, java.sql.Types.INTEGER);
			}
			if (address.getTalukaId() != null) {
				ps.setInt(4, address.getTalukaId());
			} else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
			ps.setString(5, address.getPlace());
			ps.setString(6, address.getPincode());
			ps.setLong(7, address.getAddressId());
			return ps;
		});
		return address;
	}

	@Override
	public void delete(Long addressId) {
		String sql = "DELETE FROM address WHERE address_id = ?";
		jdbcTemplate.update(sql, addressId);
	}

	@Override
    public List<Address> findByUserIds(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            logger.warn("userIds is null or empty, returning empty list");
            return Collections.emptyList();
        }

        List<Long> validUserIds = userIds.stream()
                .filter(id -> id != null)
                .distinct()
                .toList();

        if (validUserIds.isEmpty()) {
            logger.warn("No valid user IDs provided, returning empty list");
            return Collections.emptyList();
        }

        String sqlTemplate = """
                SELECT a.*, s.state_name,d.district_name ,t.taluka_name   FROM address a
                        inner join state s on a.state_id = s.id
                        left join district d  on a.district_id =d.id
                        left join taluka t on a.taluka_id = t.id WHERE a.user_id IN (%s)
                """;

        List<Address> allAddresses = new ArrayList<>();
        List<List<Long>> batches = IntStream.range(0, (validUserIds.size() + BATCH_SIZE - 1) / BATCH_SIZE)
                .mapToObj(i -> validUserIds.subList(
                        i * BATCH_SIZE,
                        Math.min((i + 1) * BATCH_SIZE, validUserIds.size())))
                .toList();

        for (int i = 0; i < batches.size(); i++) {
            List<Long> batchIds = batches.get(i);
            String placeholders = String.join(",", Collections.nCopies(batchIds.size(), "?"));
            String batchSql = sqlTemplate.formatted(placeholders);

            try {
                List<Address> batchResults = jdbcTemplate.query(
                        batchSql,
                        new AddressRowMapper(),
                        batchIds.toArray()
                );
                allAddresses.addAll(batchResults);
                logger.info("Fetched {} address records for batch {}/{} ({} userIds)",
                        batchResults.size(), i + 1, batches.size(), batchIds.size());
            } catch (Exception e) {
                logger.error("Failed to fetch address records for batch {}: {}", i + 1, e.getMessage(), e);
                throw new RuntimeException("Failed to fetch addresses for batch " + (i + 1), e);
            }
        }

        logger.info("Total fetched {} address records for {} userIds",
                allAddresses.size(), validUserIds.size());
        return allAddresses;
    }

    @Override
    public Optional<Address> findByUserId(Long userId) {
        String sql = """
                SELECT * FROM address WHERE user_id = ?
                """;
        try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new AddressRowMapper(), userId));
		} catch (Exception e) {
			return Optional.empty();
		}
    }
}
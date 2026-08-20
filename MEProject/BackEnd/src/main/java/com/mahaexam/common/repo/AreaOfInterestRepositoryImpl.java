package com.mahaexam.common.repo;

import com.mahaexam.common.model.AreaOfInterest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Repository
public class AreaOfInterestRepositoryImpl implements AreaOfInterestRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AreaOfInterestRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AreaOfInterest> rowMapper = new RowMapper<>() {
        @Override
        public AreaOfInterest mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AreaOfInterest(
                    rs.getInt("id"),
                    rs.getString("name")
            );
        }
    };

    @Override
    public AreaOfInterest save(AreaOfInterest areaOfInterest) {
        String sql = "INSERT INTO area_of_interest (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, areaOfInterest.getName());
            return ps;
        }, keyHolder);

        Integer generatedId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        areaOfInterest.setId(generatedId);
        return areaOfInterest;
    }

    @Override
    public Optional<AreaOfInterest> findById(Integer id) {
        String sql = "SELECT * FROM area_of_interest WHERE id = ?";
        List<AreaOfInterest> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<AreaOfInterest> findAll() {
        String sql = "SELECT * FROM area_of_interest";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(AreaOfInterest areaOfInterest) {
        String sql = "UPDATE area_of_interest SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, areaOfInterest.getName(), areaOfInterest.getId());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM area_of_interest WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

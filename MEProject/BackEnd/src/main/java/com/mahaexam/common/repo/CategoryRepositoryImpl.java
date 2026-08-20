package com.mahaexam.common.repo;

import com.mahaexam.common.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Category> CATEGORY_ROW_MAPPER = new RowMapper<>() {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setCategoryName(rs.getString("category_name"));
            category.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            category.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
            category.setDeletedAt(rs.getObject("deleted_at", LocalDateTime.class));
            category.setDeleted(rs.getString("deleted").equals("1"));
            category.setDisabled(rs.getBoolean("is_disabled"));
            return category;
        }
    };

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM categories WHERE deleted = '0'", CATEGORY_ROW_MAPPER);
    }

    @Override
    public Optional<Category> findById(int id) {
        List<Category> categories = jdbcTemplate.query(
                "SELECT * FROM categories WHERE id = ? AND deleted = '0'",
                CATEGORY_ROW_MAPPER, id);
        return categories.isEmpty() ? Optional.empty() : Optional.of(categories.get(0));
    }

    @Override
    public Category save(Category category) {
        jdbcTemplate.update(
                "INSERT INTO categories (category_name, created_at) VALUES (?, CURRENT_TIMESTAMP)",
                category.getCategoryName());
        // Retrieve the generated ID
        int generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        category.setId(generatedId);
        return category;
    }

    @Override
    public Category update(Category category) {
        jdbcTemplate.update(
                "UPDATE categories SET category_name = ?, updated_at = CURRENT_TIMESTAMP, is_disabled = ? WHERE id = ? AND deleted = '0'",
                category.getCategoryName(), category.isDisabled() ? 1 : 0, category.getId());
        return category;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(
                "UPDATE categories SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?",
                id);
    }

	@Override
	public boolean existsByCategoryName(String categoryName) {
		String sql = "SELECT COUNT(*) FROM categories WHERE category_name = ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, categoryName);
        return count != null && count > 0;
	}

	@Override
	public boolean existsByCategoryNameAndExceptId(String categoryName, int excludeId) {
		String sql = "SELECT COUNT(*) FROM categories WHERE category_name = ? AND id != ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, categoryName, excludeId);
        return count != null && count > 0;
	}
}


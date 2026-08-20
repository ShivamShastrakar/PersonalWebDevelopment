package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.StudentClass;

@Repository
public class StudentClassRepositoryImpl implements StudentClassRepository {
    private final JdbcTemplate jdbcTemplate;

    public StudentClassRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StudentClass save(StudentClass studentClass) {
    	String sql = "INSERT INTO student_class (student_id, class_id) VALUES (?, ?)";

    	KeyHolder keyHolder = new GeneratedKeyHolder();

    	jdbcTemplate.update(connection -> {
    	    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    	    ps.setLong(1, studentClass.getStudentId());
    	    ps.setLong(2, studentClass.getClassId());
    	    return ps;
    	}, keyHolder);

    	// Retrieve and set the generated ID
    	Number key = keyHolder.getKey();
    	if (key != null) {
    	    studentClass.setId(key.longValue());
    	}

    	return studentClass;

    }

    @Override
    public Optional<StudentClass> findById(Long id) {
        String sql = "SELECT * FROM student_class WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new StudentClassRowMapper(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<StudentClass> findAll() {
        String sql = "SELECT * FROM student_class";
        return jdbcTemplate.query(sql, new StudentClassRowMapper());
    }

    @Override
    public StudentClass update(StudentClass studentClass) {
        String sql = "UPDATE student_class SET student_id = ?, class_id = ? WHERE id = ?";
        
        jdbcTemplate.update(sql,
            studentClass.getStudentId(),
            studentClass.getClassId(),
            studentClass.getId()
        );
        return studentClass;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM student_class WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    @Override
    public void deleteStudentId(Long studentId) {
        String sql = "DELETE FROM student_class WHERE student_id = ?";
        jdbcTemplate.update(sql, studentId);
    }

    @Override
    public List<StudentClass> findByStudentId(Long studentId) {
        String sql = "SELECT * FROM student_class WHERE student_id = ?";
        return jdbcTemplate.query(sql, new StudentClassRowMapper(), studentId);
    }
}

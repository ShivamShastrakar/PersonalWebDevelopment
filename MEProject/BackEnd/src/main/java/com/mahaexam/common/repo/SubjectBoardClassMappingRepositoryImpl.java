package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.SubjectBoardClassMapping;

@Repository
public class SubjectBoardClassMappingRepositoryImpl implements SubjectBoardClassMappingRepository {

	private final JdbcTemplate jdbcTemplate;

	public SubjectBoardClassMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int save(SubjectBoardClassMapping mapping) {
		String sql = "INSERT INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql, mapping.getSubjectId(), mapping.getClassId(), mapping.getBoardId(), mapping.getMedium());
	}

	@Override
	public int softDelete(int id) {
		String sql = "UPDATE subject_board_class_mapping SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public List<SubjectBoardClassMapping> findAll() {
		String sql = "SELECT * FROM subject_board_class_mapping WHERE deleted = '0'";
		return jdbcTemplate.query(sql, new SubjectBoardClassMappingRowMapper());
	}

	@Override
	public Optional<SubjectBoardClassMapping> findById(Integer id) {
		String sql = "SELECT * FROM subject_board_class_mapping WHERE id = ? AND deleted = '0'";
		try {
			SubjectBoardClassMapping mapping = jdbcTemplate.queryForObject(sql, new SubjectBoardClassMappingRowMapper(),
					id);
			return Optional.ofNullable(mapping);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean existsBySubjectClassBoard(Integer subjectId, Integer classId, Integer boardId, String medium) {
		String sql = """
				SELECT COUNT(*) FROM subject_board_class_mapping
				WHERE subject_id = ? AND class_id = ? AND board_id = ?
				AND (medium = ? OR (medium IS NULL AND ? IS NULL)) AND deleted = '0'
				""";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, subjectId, classId, boardId, medium, medium);
		return count != null && count > 0;
	}

    @Override
    public int deleteBySubjectId(int subjectId) {
        String sql = "delete from subject_board_class_mapping WHERE subject_id = ?";
        return jdbcTemplate.update(sql, subjectId);
    }

    @Override
    public int[] save(List<SubjectBoardClassMapping> mappings) {
        String sql = "INSERT INTO subject_board_class_mapping (subject_id, board_id, class_id, medium) VALUES (?, ?, ?, ?)";

        List<Object[]> batchArgs = mappings.stream()
                .map(mapping -> new Object[]{
                        mapping.getSubjectId(),
                        mapping.getBoardId(),
                        mapping.getClassId(),
                        mapping.getMedium()
                })
                .toList();

        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }

	@Override
	public List<SubjectBoardClassMapping> findByIds(List<Integer> subjectIds) {
		if (subjectIds == null || subjectIds.isEmpty()) {
			return java.util.Collections.emptyList();
		}
		String inSql = subjectIds.stream().map(id -> "?").collect(java.util.stream.Collectors.joining(", "));
		String sql = "SELECT sbcm.*, c.class_name, b.board_name FROM subject_board_class_mapping sbcm " +
		             "INNER JOIN class c ON c.id = sbcm.class_id " +
		             "INNER JOIN board b ON b.id = sbcm.board_id " +
		             "WHERE sbcm.subject_id IN (" + inSql + ") AND sbcm.deleted = '0'";
		Object[] params = subjectIds.toArray();
		return jdbcTemplate.query(sql, params, new SubjectBoardClassMappingRowMapper());
	}

}
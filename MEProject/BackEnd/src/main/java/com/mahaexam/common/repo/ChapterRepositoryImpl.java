package com.mahaexam.common.repo;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Chapter;
import com.mahaexam.tenant.management.util.TenantResolver;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class ChapterRepositoryImpl implements ChapterRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChapterRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Chapter> findAll(UserBean user) {
        String sql= """
                SELECT ch.*,
                      GROUP_CONCAT(DISTINCT cl.class_name ORDER BY cl.class_name SEPARATOR ', ') AS class_name,
                      GROUP_CONCAT(DISTINCT b.board_name ORDER BY b.board_name SEPARATOR ', ')   AS board_name,
                      s.subject_name as subject_name
                   FROM chapters ch
                   LEFT JOIN chapter_board_class_mapping m ON m.chapter_id = ch.id
                   LEFT JOIN class cl ON m.class_id = cl.id
                   LEFT JOIN board b ON m.board_id = b.id
                   LEFT JOIN subject s ON ch.subject_id = s.subject_id
                   WHERE (ch.tenant_id =?  OR ch.tenant_id IS NULL)
                     AND (ch.status = '0' OR ch.status IS NULL)
                   GROUP BY ch.id
                   ORDER BY ch.id
                """;
        return jdbcTemplate.query(sql, new ChapterRowMapper(), user.getTenantId());
    }

    @Override
    public Chapter findById(int id) {
        String sql = "SELECT * FROM chapters WHERE id = ? ";
        try {
            return jdbcTemplate.queryForObject(sql, new ChapterRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }

    @Override
    public Chapter save(Chapter chapter) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO chapters (chapter_name, unit, status, exam_type, subject_id, institute_id, class, tenant_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, chapter.getChapterName());
            ps.setString(2, chapter.getUnit());
            ps.setInt(3, 0);
            ps.setString(4, chapter.getExamType());
            ps.setObject(5, chapter.getSubjectId());
            ps.setObject(6, chapter.getInstituteId());
            ps.setObject(7, chapter.getClassName());
            ps.setObject(8, chapter.getTenantId());
            return ps;
        }, keyHolder);

        // Retrieve the generated ID
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            chapter.setId(generatedId.intValue());
        }
        return chapter;
    }

    @Override
    public Chapter update(Chapter chapter) {
        jdbcTemplate.update(
                "UPDATE chapters SET chapter_name = ?, unit = ?,  exam_type = ?, subject_id = ?, " +
                        " institute_id = ?, class = ?, updated_at = CURRENT_TIMESTAMP " +
                        "WHERE id = ? ",
                chapter.getChapterName(), chapter.getUnit(), chapter.getExamType(),
                chapter.getSubjectId(),  chapter.getInstituteId(),
                chapter.getClassName(),  chapter.getId());
        return chapter;
    }

    @Override
    public int softDelete(int id) {
        String sql = "UPDATE chapters SET status=1 , deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByChapterName(String chapterName, int subjectId, int classId, int chapterId) {
        String sql = "SELECT COUNT(*) FROM chapters WHERE chapter_name = ? AND subject_id = ? AND class = ? AND id != ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, chapterName, subjectId, classId, chapterId);
        return count != null && count > 0;
    }

    @Override
    public Chapter getBySubjectId(int subjectId) {
        String sql = "SELECT * FROM chapters WHERE subject_id = ? AND deleted = '0'";
        try {
            return jdbcTemplate.queryForObject(sql, new ChapterRowMapper(), subjectId);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }

    @Override
    public List<Chapter> findByBoardClassSubjectAndMedium(Integer boardId, Integer classId, Integer subjectId, String medium, Long tenantId) {
        String sql = """
                     SELECT DISTINCT ch.*, sc.coverage_percentage, s.subject_name as subject_name  FROM chapters ch
                        INNER JOIN chapter_board_class_mapping cbcm ON ch.id = cbcm.chapter_id
                        INNER JOIN syllabus sy on ch.class_id  =sy.class_id\s
                        INNER JOIN syllabus_chapter sc on ch.id =sc.chapter_id and sy.id =sc.syllabus_id\s
                        INNER JOIN subject s ON ch.subject_id = s.subject_id
                        INNER JOIN subject_board_class_mapping sbcm ON s.subject_id = sbcm.subject_id
                        WHERE cbcm.board_id = ? AND cbcm.class_id = ? and ch.subject_id=? and sy.status ='ACTIVE' and sbcm.medium =?
                        AND (ch.tenant_id = ? OR ch.tenant_id IS NULL) AND (ch.status = '0' or ch.status IS null)
                        ORDER BY ch.chapter_name;
                """;
        return jdbcTemplate.query(sql, new ChapterRowMapper(), boardId, classId, subjectId,medium,  tenantId);
    }
}

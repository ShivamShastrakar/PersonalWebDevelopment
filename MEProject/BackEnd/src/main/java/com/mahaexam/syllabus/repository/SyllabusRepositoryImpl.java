package com.mahaexam.syllabus.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.support.KeyHolder;

import com.mahaexam.common.model.Board;
import com.mahaexam.common.model.ClassEntity;
import com.mahaexam.common.model.Subject;
import com.mahaexam.common.repo.BoardRepository;
import com.mahaexam.common.repo.ClassRepository;
import com.mahaexam.common.repo.SubjectRepository;
import com.mahaexam.papertemplate.model.Syllabus;

@Repository
public class SyllabusRepositoryImpl implements SyllabusRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BoardRepository boardRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;

    public SyllabusRepositoryImpl(
            NamedParameterJdbcTemplate jdbcTemplate , BoardRepository boardRepository,
            ClassRepository classRepository,
            SubjectRepository subjectRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.boardRepository = boardRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public boolean existsByClassIdAndSubjectIdAndBoardIdAndMediumAndAcademicYearAndTenantId(
            Long classId,
            Long subjectId,
            Long boardId,
            String medium,
            Integer academicYear,
            Long tenantId) {

        String sql = """
            SELECT COUNT(*) FROM syllabus
            WHERE class_id = :classId
              AND subject_id = :subjectId
              AND board_id = :boardId
              AND medium = :medium
              AND academic_year = :year
              AND tenant_id = :tenantId
        """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource()
                        .addValue("classId", classId)
                        .addValue("subjectId", subjectId)
                        .addValue("boardId", boardId)
                        .addValue("medium", medium)
                        .addValue("year", academicYear)
                        .addValue("tenantId", tenantId),
                Integer.class
        );

        return count != null && count > 0;
    }

    @Override
    public Syllabus save(Syllabus syllabus) {

        String sql = """
            INSERT INTO syllabus
            (class_id, subject_id, board_id, medium, academic_year, status, tenant_id, created_by, updated_by, name)
            VALUES (:classId, :subjectId, :boardId, :medium, :year, :status, :tenantId, :createdBy, :updatedBy, :name)
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("classId", syllabus.getClassId())
                .addValue("subjectId", syllabus.getSubjectId())
                .addValue("boardId", syllabus.getBoardId())
                .addValue("medium", syllabus.getMedium())
                .addValue("year", syllabus.getAcademicYear())
                .addValue("status", syllabus.getStatus())
                .addValue("tenantId", syllabus.getTenantId())
                .addValue("createdBy", syllabus.getCreatedBy())
                .addValue("updatedBy", syllabus.getUpdatedBy())
                .addValue("name", syllabus.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        syllabus.setId(
                Objects.requireNonNull(keyHolder.getKey()).longValue()
        );

        return syllabus;
    }


    @Override
    public Optional<Syllabus> findByClassIdAndSubjectIdAndMediumAndAcademicYearAndTenantId(
            Long classId,
            Long subjectId,
            String medium,
            Integer academicYear,
            Long tenantId) {

        String sql = """
            SELECT * FROM syllabus
            WHERE class_id = :classId
              AND subject_id = :subjectId
              AND medium = :medium
              AND academic_year = :year
              AND tenant_id = :tenantId
        """;

        List<Syllabus> list = jdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("classId", classId)
                        .addValue("subjectId", subjectId)
                        .addValue("medium", medium)
                        .addValue("year", academicYear)
                        .addValue("tenantId", tenantId),
                (rs, rn) -> {
                    Syllabus s = new Syllabus();
                    s.setId((long) rs.getInt("id"));
                    s.setClassId((long) rs.getInt("class_id"));
                    s.setSubjectId((long) rs.getInt("subject_id"));
                    Integer boardId = rs.getObject("board_id", Integer.class);
                    s.setBoardId(boardId != null ? boardId.longValue() : null);
                    s.setMedium(rs.getString("medium"));
                    s.setAcademicYear(rs.getInt("academic_year"));
                    s.setStatus(rs.getString("status"));
                    s.setTenantId(rs.getObject("tenant_id", Long.class));
                    s.setCreatedBy(rs.getObject("created_by", Long.class));
                    s.setUpdatedBy(rs.getObject("updated_by", Long.class));
                    s.setName(rs.getString("name"));
                    return s;
                }
        );

        return list.isEmpty()
                ? Optional.empty()
                : Optional.of(list.get(0));
    }

    @Override
    public Optional<Syllabus> findById(Long id) {
        String sql = """
            SELECT * FROM syllabus
            WHERE id = :id
        """;

        List<Syllabus> list = jdbcTemplate.query(
                sql,
                new MapSqlParameterSource("id", id),
                (rs, rn) -> {
                    Syllabus s = new Syllabus();
                    s.setId((long) rs.getInt("id"));
                    s.setClassId((long) rs.getInt("class_id"));
                    s.setSubjectId((long) rs.getInt("subject_id"));
                    Integer boardId = rs.getObject("board_id", Integer.class);
                    s.setBoardId(boardId != null ? boardId.longValue() : null);
                    s.setMedium(rs.getString("medium"));
                    s.setAcademicYear(rs.getInt("academic_year"));
                    s.setStatus(rs.getString("status"));
                    s.setTenantId(rs.getObject("tenant_id", Long.class));
                    s.setCreatedBy(rs.getObject("created_by", Long.class));
                    s.setUpdatedBy(rs.getObject("updated_by", Long.class));
                    s.setName(rs.getString("name"));
                    return s;
                }
        );

        return list.isEmpty()
                ? Optional.empty()
                : Optional.of(list.get(0));
    }

	/*
	 * @Override public void update(Syllabus syllabus) { String sql = """ UPDATE
	 * syllabus SET class_id = :classId, subject_id = :subjectId, board_id =
	 * :boardId, medium = :medium, academic_year = :year, status = :status,
	 * tenant_id = :tenantId, created_by = :createdBy, updated_by = :updatedBy, name
	 * = :name WHERE id = :id """;
	 * 
	 * MapSqlParameterSource params = new MapSqlParameterSource() .addValue("id",
	 * syllabus.getId()) .addValue("classId", syllabus.getClassId())
	 * .addValue("subjectId", syllabus.getSubjectId()) .addValue("boardId",
	 * syllabus.getBoardId()) .addValue("medium", syllabus.getMedium())
	 * .addValue("year", syllabus.getAcademicYear()) .addValue("status",
	 * syllabus.getStatus()) .addValue("tenantId", syllabus.getTenantId())
	 * .addValue("createdBy", syllabus.getCreatedBy()) .addValue("updatedBy",
	 * syllabus.getUpdatedBy()) .addValue("name", syllabus.getName());
	 * 
	 * jdbcTemplate.update(sql, params); }
	 */
    
    @Override
    @Transactional
    public void update(Syllabus syllabus) {

        // 1. Update syllabus
        String sql = """
            UPDATE syllabus
            SET class_id = :classId,
                subject_id = :subjectId,
                board_id = :boardId,
                medium = :medium,
                academic_year = :year,
                status = :status,
                tenant_id = :tenantId,
                created_by = :createdBy,
                updated_by = :updatedBy,
                name = :name
            WHERE id = :id
        """;
     // Generate name from BoardName, Medium, ClassName, SubjectName, Academic Year
        String syllabusName = generateSyllabusName(
        		syllabus.getBoardId(),
                syllabus.getClassId(),
                syllabus.getSubjectId(),
                syllabus.getMedium(),
                syllabus.getAcademicYear()
        );
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", syllabus.getId())
                .addValue("classId", syllabus.getClassId())
                .addValue("subjectId", syllabus.getSubjectId())
                .addValue("boardId", syllabus.getBoardId())
                .addValue("medium", syllabus.getMedium())
                .addValue("year", syllabus.getAcademicYear())
                .addValue("status", syllabus.getStatus())
                .addValue("tenantId", syllabus.getTenantId())
                .addValue("createdBy", syllabus.getCreatedBy())
                .addValue("updatedBy", syllabus.getUpdatedBy())
                .addValue("name", syllabus.getName());

        jdbcTemplate.update(sql, params);

        // 2. Delete old chapter mappings
        String deleteSql = "DELETE FROM syllabus_chapter WHERE syllabus_id = :syllabusId";

        jdbcTemplate.update(deleteSql,
                new MapSqlParameterSource("syllabusId", syllabus.getId()));

        // 3. Insert new chapter mappings (with extra fields)
        String insertSql = """
            INSERT INTO syllabus_chapter 
            (syllabus_id, chapter_id, number_of_questions, coverage_percentage, marks)
            VALUES 
            (:syllabusId, :chapterId, :numQuestions, :coverage, :marks)
        """;

        List<MapSqlParameterSource> batchParams = syllabus.getChapters().stream()
                .map(ch -> new MapSqlParameterSource()
                        .addValue("syllabusId", syllabus.getId())
                        .addValue("chapterId", syllabus.getChapters().get(0).getChapterId())
                        .addValue("numQuestions", ch.getNumberOfQuestions())
                        .addValue("coverage", ch.getCoveragePercentage())
                        .addValue("marks", ch.getMarks()))
                .toList();

        jdbcTemplate.batchUpdate(insertSql,
                batchParams.toArray(new MapSqlParameterSource[0]));
    }

    @Override
    public List<Syllabus> findAll(Long tenantId, String status) {
        StringBuilder sql = new StringBuilder("""
           SELECT s.*, b.board_name, c.class_name, su.subject_name
           FROM syllabus s
           INNER JOIN board b ON s.board_id = b.id
           INNER JOIN class c ON s.class_id = c.id
           INNER JOIN subject su ON s.subject_id = su.subject_id
           WHERE 1=1
        """);

        MapSqlParameterSource params = new MapSqlParameterSource();

        // Add optional tenant_id filter
        if (tenantId != null) {
            sql.append(" AND (s.tenant_id = :tenantId OR s.tenant_id IS NULL)");
            params.addValue("tenantId", tenantId);
        }

        // Add optional status filter
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND s.status = :status");
            params.addValue("status", status);
        }

        sql.append(" ORDER BY s.id DESC");

        return jdbcTemplate.query(
                sql.toString(),
                params,
                (rs, rn) -> {
                    Syllabus s = new Syllabus();
                    s.setId((long) rs.getInt("id"));
                    s.setClassId((long) rs.getInt("class_id"));
                    s.setSubjectId((long) rs.getInt("subject_id"));
                    Integer boardId = rs.getObject("board_id", Integer.class);
                    s.setBoardId(boardId != null ? boardId.longValue() : null);
                    s.setMedium(rs.getString("medium"));
                    s.setAcademicYear(rs.getInt("academic_year"));
                    s.setStatus(rs.getString("status"));
                    s.setTenantId(rs.getObject("tenant_id", Long.class));
                    s.setCreatedBy(rs.getObject("created_by", Long.class));
                    s.setUpdatedBy(rs.getObject("updated_by", Long.class));
                    s.setName(rs.getString("name"));

                    // Map joined table columns
                    s.setBoardName(rs.getString("board_name"));
                    s.setClassName(rs.getString("class_name"));
                    s.setSubjectName(rs.getString("subject_name"));

                    return s;
                }
        );
    }
    
    @Override
    public int softDelete(int id) {
        String sql = "UPDATE syllabus SET status = 'INACTIVE' WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.update(sql, params);
    }
    
    /* -------------------------------------------------
     * GENERATE SYLLABUS NAME
     * Format: BoardName_Medium_ClassName_SubjectName_AcademicYear
     * ------------------------------------------------- */
    private String generateSyllabusName(
            Long boardId,
            Long classId,
            Long subjectId,
            String medium,
            Integer academicYear) {

        Board board = boardRepository.findById(boardId.intValue());
        ClassEntity classEntity = classRepository.findById(classId.intValue());
        Subject subject = subjectRepository.findById(subjectId.intValue());

        if (board == null) {
            throw new RuntimeException("Board not found with id: " + boardId);
        }
        if (classEntity == null) {
            throw new RuntimeException("Class not found with id: " + classId);
        }
        if (subject == null) {
            throw new RuntimeException("Subject not found with id: " + subjectId);
        }

        return String.format("Board%s_Medium%s_Class%s_Subject%s_Year%d",
                board.getBoardName().replaceAll("\\s+", "_"),
                medium.replaceAll("\\s+", "_"),
                classEntity.getClassName().replaceAll("\\s+", "_"),
                subject.getSubjectName().replaceAll("\\s+", "_"),
                academicYear
        );
    }

}

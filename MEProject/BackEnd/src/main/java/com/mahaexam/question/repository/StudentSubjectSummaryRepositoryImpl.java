package com.mahaexam.question.repository;

import com.mahaexam.common.bean.StudentExamSummaryDTO;
import com.mahaexam.model.StudentSubjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

@Repository
public class StudentSubjectSummaryRepositoryImpl implements StudentSubjectSummaryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<StudentSubjectSummary> rowMapper = (rs, rowNum) -> {
        StudentSubjectSummary summary = new StudentSubjectSummary();
        summary.setId(rs.getLong("id"));
        summary.setQuestionPaperId(rs.getLong("question_paper_id"));
        summary.setStudentUserId(rs.getLong("student_user_id"));
        summary.setSubjectId(rs.getInt("subject_id"));
        summary.setTotalQuestions(rs.getInt("total_questions"));
        summary.setCorrect(rs.getInt("correct"));
        summary.setWrong(rs.getInt("wrong"));
        summary.setNotAnswered(rs.getInt("not_answered"));
        summary.setMarksObtained(rs.getBigDecimal("marks_obtained"));
        summary.setMaxMarks(rs.getBigDecimal("max_marks"));
        summary.setTimeTaken(rs.getObject("time_taken") != null ? rs.getInt("time_taken") : null);
        summary.setAttemptedAt(rs.getTimestamp("attempted_at") != null ? rs.getTimestamp("attempted_at").toLocalDateTime() : null);
        summary.setTenantId(rs.getObject("tenant_id") != null ? rs.getLong("tenant_id") : null);
        return summary;
    };

    @Override
    public List<StudentSubjectSummary> findAllByTenantId(Long tenantId) {
        String sql = "SELECT * FROM student_subject_summary WHERE tenant_id = ?";
        return jdbcTemplate.query(sql, rowMapper, tenantId);
    }

    @Override
    public StudentSubjectSummary findByIdAndTenantId(Long id, Long tenantId) {
        String sql = "SELECT * FROM student_subject_summary WHERE id = ? AND tenant_id = ?";
        List<StudentSubjectSummary> results = jdbcTemplate.query(sql, rowMapper, id, tenantId);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public int save(StudentSubjectSummary summary) {
        String sql = "INSERT INTO student_subject_summary (question_paper_id, student_user_id, subject_id, total_questions, correct, wrong, not_answered, marks_obtained, max_marks, time_taken, attempted_at, tenant_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(
            new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, summary.getQuestionPaperId());
                    ps.setLong(2, summary.getStudentUserId());
                    ps.setInt(3, summary.getSubjectId());
                    ps.setInt(4, summary.getTotalQuestions());
                    ps.setInt(5, summary.getCorrect());
                    ps.setInt(6, summary.getWrong());
                    ps.setInt(7, summary.getNotAnswered());
                    ps.setBigDecimal(8, summary.getMarksObtained());
                    ps.setBigDecimal(9, summary.getMaxMarks());
                    if (summary.getTimeTaken() != null) {
                        ps.setInt(10, summary.getTimeTaken());
                    } else {
                        ps.setNull(10, java.sql.Types.INTEGER);
                    }
                    if (summary.getAttemptedAt() != null) {
                        ps.setTimestamp(11, java.sql.Timestamp.valueOf(summary.getAttemptedAt()));
                    } else {
                        ps.setNull(11, java.sql.Types.TIMESTAMP);
                    }
                    if (summary.getTenantId() != null) {
                        ps.setLong(12, summary.getTenantId());
                    } else {
                        ps.setNull(12, java.sql.Types.BIGINT);
                    }
                    return ps;
                }
            },
            keyHolder);

        if (keyHolder.getKey() != null) {
            summary.setId(keyHolder.getKey().longValue());
        }
        return rows;
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM student_subject_summary WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }


    @Override
    public StudentSubjectSummary findByPaperStudentSubjectAndTenantId(Long questionPaperId, Long studentUserId, Integer subjectId, Long tenantId) {
        String sql = "SELECT * FROM student_subject_summary WHERE question_paper_id = ? AND student_user_id = ? AND subject_id = ? AND tenant_id = ?";
        List<StudentSubjectSummary> summaries = jdbcTemplate.query(sql, rowMapper, questionPaperId, studentUserId, subjectId, tenantId);
        return summaries.isEmpty() ? null : summaries.get(0);
    }

    @Override
    public List<StudentExamSummaryDTO> findStudentExamSummary(Long studentUserId, Long tenantId) {
        /*
         * Returns one row per active package the student is enrolled in.
         * Each row contains:
         *   - package info (id, name, start/end date)
         *   - student's class and medium (from student profile)
         *   - totalExams  : COUNT of distinct active question papers in that package
         *                   whose paper_template matches the student's class + medium
         *   - takenCount  : COUNT of those papers the student has already attempted
         */
        String sql = """
                SELECT
                    p.id                                           AS package_id,
                    p.package_name                                 AS package_name,
                    p.start_date                                   AS package_start_date,
                    p.end_date                                     AS package_end_date,
                    s.current_class_id                             AS class_id,
                    c.class_name                                   AS class_name,
                    s.medium                                       AS medium,
                    COUNT(DISTINCT qp.id)                         AS total_exams,
                    COUNT(DISTINCT sss.question_paper_id)         AS taken_count
                FROM student s
                JOIN class c
                    ON c.id = s.current_class_id
                JOIN student_package_mapping spm
                    ON spm.student_id = s.student_id
                    AND spm.status    = 'Active'
                JOIN packages p
                    ON p.id       = spm.package_id
                    AND p.deleted = '0'
                JOIN package_question_paper_mapping pqpm
                    ON pqpm.package_id = p.id
                JOIN question_paper qp
                    ON qp.id         = pqpm.question_paper_id
                    AND qp.tenant_id = ?
                JOIN question_paper_template qpt
                    ON qpt.question_paper_id = qp.id
                JOIN paper_template pt
                    ON pt.id        = qpt.paper_template_id
                    AND pt.class_id = s.current_class_id
                    AND pt.medium   = s.medium
                LEFT JOIN student_subject_summary sss
                    ON sss.question_paper_id = qp.id
                    AND sss.student_user_id  = ?
                    AND sss.tenant_id        = ?
                WHERE s.user_id = ?
                GROUP BY
                    p.id, p.package_name, p.start_date, p.end_date,
                    s.current_class_id, c.class_name, s.medium
                ORDER BY p.start_date DESC
                """;

        RowMapper<StudentExamSummaryDTO> dtoMapper = (rs, rowNum) -> StudentExamSummaryDTO.builder()
                .packageId(rs.getInt("package_id"))
                .packageName(rs.getString("package_name"))
                .packageStartDate(rs.getDate("package_start_date") != null ? rs.getDate("package_start_date").toLocalDate() : null)
                .packageEndDate(rs.getDate("package_end_date") != null ? rs.getDate("package_end_date").toLocalDate() : null)
                .classId(rs.getInt("class_id"))
                .className(rs.getString("class_name"))
                .medium(rs.getString("medium"))
                .totalExams(rs.getInt("total_exams"))
                .takenCount(rs.getInt("taken_count"))
                .build();

        return jdbcTemplate.query(sql, dtoMapper,
                tenantId,       // qp.tenant_id
                studentUserId,  // sss.student_user_id
                tenantId,       // sss.tenant_id
                studentUserId); // s.user_id (WHERE)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // STUDENT DASHBOARD
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public com.mahaexam.common.bean.StudentDashboardDTO findStudentDashboard(Long studentUserId, Long tenantId) {

        // ── 1. Package summary ────────────────────────────────────────────────
        String packageSql = """
                SELECT
                    COUNT(DISTINCT p.id)                                                     AS total_packages,
                    SUM(CASE WHEN spm.status = 'Active' THEN 1 ELSE 0 END)                  AS active_packages,
                    SUM(CASE WHEN spm.status = 'Active'
                              AND p.end_date BETWEEN CURDATE()
                              AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) THEN 1 ELSE 0 END)   AS expiring_soon
                FROM student s
                JOIN student_package_mapping spm ON spm.student_id = s.student_id
                JOIN packages p                  ON p.id = spm.package_id AND p.deleted = '0'
                WHERE s.user_id = ?
                """;

        com.mahaexam.common.bean.StudentDashboardDTO.PackageSummary packageSummary =
                jdbcTemplate.queryForObject(packageSql, (rs, rowNum) ->
                        com.mahaexam.common.bean.StudentDashboardDTO.PackageSummary.builder()
                                .totalPackages(rs.getInt("total_packages"))
                                .activePackages(rs.getInt("active_packages"))
                                .expiringSoonPackages(rs.getInt("expiring_soon"))
                                .build(),
                        studentUserId);

        // ── 2. Last exam (most recently attempted) ────────────────────────────
        String lastExamSql = """
                SELECT
                    qp.question_paper_name,
                    SUM(sss.marks_obtained) AS marks_obtained,
                    SUM(sss.max_marks)      AS max_marks,
                    MAX(sss.attempted_at)   AS attempted_at
                FROM student_subject_summary sss
                JOIN question_paper qp ON qp.id = sss.question_paper_id
                WHERE sss.student_user_id = ?
                  AND sss.tenant_id       = ?
                GROUP BY qp.id, qp.question_paper_name
                ORDER BY MAX(sss.attempted_at) DESC
                LIMIT 1
                """;

        String lastExamName = null;
        java.math.BigDecimal lastExamScore = null;
        java.time.LocalDateTime lastExamAttemptedAt = null;

        // Use plain query for nullable result
        List<java.util.Map<String, Object>> lastExamRows = jdbcTemplate.queryForList(
                lastExamSql, studentUserId, tenantId);
        if (!lastExamRows.isEmpty()) {
            java.util.Map<String, Object> row = lastExamRows.get(0);
            lastExamName = (String) row.get("question_paper_name");
            java.math.BigDecimal mo = (java.math.BigDecimal) row.get("marks_obtained");
            java.math.BigDecimal mm = (java.math.BigDecimal) row.get("max_marks");
            if (mo != null && mm != null && mm.compareTo(java.math.BigDecimal.ZERO) != 0) {
                lastExamScore = mo.multiply(new java.math.BigDecimal("100"))
                        .divide(mm, 2, java.math.RoundingMode.HALF_UP);
            }
            Object ts = row.get("attempted_at");
            if (ts instanceof java.sql.Timestamp) {
                lastExamAttemptedAt = ((java.sql.Timestamp) ts).toLocalDateTime();
            }
        }

        // ── 3. Upcoming exam — next untaken ACTIVE paper for this student ─────
        String upcomingSql = """
                SELECT DISTINCT
                    qp.id,
                    qp.question_paper_name,
                    qp.start_date,
                    qp.end_date,
                    pt.total_duration,
                    pt.total_marks,
                    pt.number_of_parts
                FROM question_paper qp
                JOIN package_question_paper_mapping pqpm ON pqpm.question_paper_id = qp.id
                JOIN packages p    ON p.id = pqpm.package_id AND p.deleted = '0'
                JOIN student_package_mapping spm ON spm.package_id = p.id AND spm.status = 'Active'
                JOIN student s     ON s.student_id = spm.student_id AND s.user_id = ?
                JOIN question_paper_template qpt ON qpt.question_paper_id = qp.id
                JOIN paper_template pt ON pt.id = qpt.paper_template_id
                                     AND pt.class_id = s.current_class_id
                                     AND pt.medium   = s.medium
                LEFT JOIN (
                    SELECT DISTINCT question_paper_id
                    FROM student_subject_summary
                    WHERE student_user_id = ? AND tenant_id = ?
                ) taken ON taken.question_paper_id = qp.id
                WHERE qp.tenant_id = ?
                  AND qp.status    = 'ACTIVE'
                  AND taken.question_paper_id IS NULL
                ORDER BY qp.start_date ASC
                LIMIT 1
                """;

        com.mahaexam.common.bean.StudentDashboardDTO.UpcomingExam upcomingExam = null;
        List<java.util.Map<String, Object>> upcomingRows = jdbcTemplate.queryForList(
                upcomingSql, studentUserId, studentUserId, tenantId, tenantId);
        if (!upcomingRows.isEmpty()) {
            java.util.Map<String, Object> r = upcomingRows.get(0);
            upcomingExam = com.mahaexam.common.bean.StudentDashboardDTO.UpcomingExam.builder()
                    .questionPaperId(((Number) r.get("id")).longValue())
                    .questionPaperName((String) r.get("question_paper_name"))
                    .totalDuration(r.get("total_duration") != null ? ((Number) r.get("total_duration")).intValue() : null)
                    .examTotalMarks(r.get("total_marks") != null ? ((Number) r.get("total_marks")).intValue() : null)
                    .startDate(r.get("start_date") instanceof java.sql.Timestamp
                            ? ((java.sql.Timestamp) r.get("start_date")).toLocalDateTime() : null)
                    .endDate(r.get("end_date") instanceof java.sql.Timestamp
                            ? ((java.sql.Timestamp) r.get("end_date")).toLocalDateTime() : null)
                    .build();
        }

        // ── 4. Recent results (last 5 taken exams with score %) ───────────────
        String recentSql = """
                SELECT
                    qp.id                         AS question_paper_id,
                    MIN(sss.id)                   AS summary_id,
                    qp.question_paper_name,
                    SUM(sss.marks_obtained)       AS marks_obtained,
                    SUM(sss.max_marks)            AS max_marks,
                    MAX(sss.attempted_at)         AS attempted_at
                FROM student_subject_summary sss
                JOIN question_paper qp ON qp.id = sss.question_paper_id
                WHERE sss.student_user_id = ?
                  AND sss.tenant_id       = ?
                GROUP BY qp.id, qp.question_paper_name
                ORDER BY MAX(sss.attempted_at) DESC
                LIMIT 5
                """;

        List<com.mahaexam.common.bean.StudentDashboardDTO.RecentResult> recentResults =
                jdbcTemplate.query(recentSql, (rs, rowNum) -> {
                    java.math.BigDecimal mo = rs.getBigDecimal("marks_obtained");
                    java.math.BigDecimal mm = rs.getBigDecimal("max_marks");
                    java.math.BigDecimal pct = null;
                    if (mo != null && mm != null && mm.compareTo(java.math.BigDecimal.ZERO) != 0) {
                        pct = mo.multiply(new java.math.BigDecimal("100"))
                                .divide(mm, 2, java.math.RoundingMode.HALF_UP);
                    }
                    return com.mahaexam.common.bean.StudentDashboardDTO.RecentResult.builder()
                            .questionPaperId(rs.getLong("question_paper_id"))
                            .summaryId(rs.getLong("summary_id"))
                            .questionPaperName(rs.getString("question_paper_name"))
                            .marksObtained(mo)
                            .maxMarks(mm)
                            .scorePercent(pct)
                            .attemptedAt(rs.getTimestamp("attempted_at") != null
                                    ? rs.getTimestamp("attempted_at").toLocalDateTime() : null)
                            .build();
                }, studentUserId, tenantId);

        // ── Assemble and return ────────────────────────────────────────────────
        return com.mahaexam.common.bean.StudentDashboardDTO.builder()
                .packages(packageSummary)
                .lastExamName(lastExamName)
                .lastExamScore(lastExamScore)
                .lastExamAttemptedAt(lastExamAttemptedAt)
                .upcomingExam(upcomingExam)
                .recentResults(recentResults)
                .build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TILE 1 – Package summary
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public com.mahaexam.common.bean.PackageSummaryResponse findDashboardPackages(Long studentUserId) {
        String sql = """
                SELECT
                    COUNT(DISTINCT p.id)                                                   AS total_packages,
                    SUM(CASE WHEN spm.status = 'Active' THEN 1 ELSE 0 END)                AS active_packages,
                    SUM(CASE WHEN spm.status = 'Active'
                              AND p.end_date BETWEEN CURDATE()
                              AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) THEN 1 ELSE 0 END) AS expiring_soon
                FROM student s
                JOIN student_package_mapping spm ON spm.student_id = s.student_id
                JOIN packages p                  ON p.id = spm.package_id AND p.deleted = '0'
                WHERE s.user_id = ?
                """;
        com.mahaexam.common.bean.PackageSummaryResponse result =
                jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        com.mahaexam.common.bean.PackageSummaryResponse.builder()
                                .totalPackages(rs.getInt("total_packages"))
                                .activePackages(rs.getInt("active_packages"))
                                .expiringSoonPackages(rs.getInt("expiring_soon"))
                                .build(),
                        studentUserId);
        return result != null ? result :
                com.mahaexam.common.bean.PackageSummaryResponse.builder()
                        .totalPackages(0).activePackages(0).expiringSoonPackages(0).build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TILE 2 – Upcoming exam
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public com.mahaexam.common.bean.UpcomingExamResponse findDashboardUpcomingExam(Long studentUserId, Long tenantId) {
        String sql = """
                SELECT DISTINCT
                    qp.id,
                    qp.question_paper_name,
                    qp.start_date,
                    qp.end_date,
                    pt.total_duration,
                    pt.total_marks,
                    pt.number_of_parts
                FROM question_paper qp
                JOIN package_question_paper_mapping pqpm ON pqpm.question_paper_id = qp.id
                JOIN packages p    ON p.id = pqpm.package_id AND p.deleted = '0'
                JOIN student_package_mapping spm ON spm.package_id = p.id AND spm.status = 'Active'
                JOIN student s     ON s.student_id = spm.student_id AND s.user_id = ?
                JOIN question_paper_template qpt ON qpt.question_paper_id = qp.id
                JOIN paper_template pt ON pt.id = qpt.paper_template_id
                                     AND pt.class_id = s.current_class_id
                                     AND pt.medium   = s.medium
                LEFT JOIN (
                    SELECT DISTINCT question_paper_id
                    FROM student_subject_summary
                    WHERE student_user_id = ? AND tenant_id = ?
                ) taken ON taken.question_paper_id = qp.id
                WHERE qp.tenant_id = ?
                  AND qp.status    = 'ACTIVE'
                  AND taken.question_paper_id IS NULL
                  AND  NOW()>=qp.start_date and NOW()<=qp.end_date
                ORDER BY qp.start_date ASC
                LIMIT 1
                """;
        List<java.util.Map<String, Object>> rows = jdbcTemplate.queryForList(
                sql, studentUserId, studentUserId, tenantId, tenantId);
        if (rows.isEmpty()) return null;
        java.util.Map<String, Object> r = rows.get(0);
        return com.mahaexam.common.bean.UpcomingExamResponse.builder()
                .questionPaperId(((Number) r.get("id")).longValue())
                .questionPaperName((String) r.get("question_paper_name"))
                .totalDuration(r.get("total_duration") != null ? ((Number) r.get("total_duration")).intValue() : null)
                .examTotalMarks(r.get("total_marks") != null ? ((Number) r.get("total_marks")).intValue() : null)
                .startDate(r.get("start_date") instanceof java.sql.Timestamp
                        ? ((java.sql.Timestamp) r.get("start_date")).toLocalDateTime() : null)
                .endDate(r.get("end_date") instanceof java.sql.Timestamp
                        ? ((java.sql.Timestamp) r.get("end_date")).toLocalDateTime() : null)
                .build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TILE 3 – Recent results
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public List<com.mahaexam.common.bean.RecentResultResponse> findDashboardRecentResults(Long studentUserId, Long tenantId) {
        String sql = """
                SELECT
                    qp.id                         AS question_paper_id,
                    MIN(sss.id)                   AS summary_id,
                    qp.question_paper_name,
                    SUM(sss.marks_obtained)       AS marks_obtained,
                    SUM(sss.max_marks)            AS max_marks,
                    MAX(sss.attempted_at)         AS attempted_at
                FROM student_subject_summary sss
                JOIN question_paper qp ON qp.id = sss.question_paper_id
                WHERE sss.student_user_id = ?
                  AND sss.tenant_id       = ?
                GROUP BY qp.id, qp.question_paper_name
                ORDER BY MAX(sss.attempted_at) DESC
                LIMIT 2
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            java.math.BigDecimal mo = rs.getBigDecimal("marks_obtained");
            java.math.BigDecimal mm = rs.getBigDecimal("max_marks");
            java.math.BigDecimal pct = null;
            if (mo != null && mm != null && mm.compareTo(java.math.BigDecimal.ZERO) != 0) {
                pct = mo.multiply(new java.math.BigDecimal("100"))
                        .divide(mm, 2, java.math.RoundingMode.HALF_UP);
            }
            return com.mahaexam.common.bean.RecentResultResponse.builder()
                    .questionPaperId(rs.getLong("question_paper_id"))
                    .summaryId(rs.getLong("summary_id"))
                    .questionPaperName(rs.getString("question_paper_name"))
                    .marksObtained(mo)
                    .maxMarks(mm)
                    .scorePercent(pct)
                    .attemptedAt(rs.getTimestamp("attempted_at") != null
                            ? rs.getTimestamp("attempted_at").toLocalDateTime() : null)
                    .build();
        }, studentUserId, tenantId);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PACKAGE-WISE EXAM RESULTS
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public List<com.mahaexam.common.bean.PackageExamResultDTO> findExamResultsByPackage(Long studentUserId, Long tenantId) {
        /*
         * One row per (package, question_paper).
         *
         * exam_stats  – pre-aggregates the student's results per (package, paper)
         * pkg_totals  – derived from the same base joins to count total_exams /
         *               taken_count per package, avoiding a full second join chain.
         *
         * Parameters: tenantId, studentUserId, tenantId, studentUserId
         */
        String sql = """
                SELECT
                    p.id                        AS package_id,
                    p.package_name,
                    p.start_date                AS package_start_date,
                    p.end_date                  AS package_end_date,
                    s.current_class_id          AS class_id,
                    c.class_name,
                    s.medium,
                    qp.id                       AS question_paper_id,
                    qp.question_paper_name,
                    pt.total_duration,
                    pt.total_marks,
                    es.summary_id,
                    CASE WHEN es.summary_id IS NOT NULL THEN 1 ELSE 0 END AS is_taken,
                    es.marks_obtained,
                    es.max_marks,
                    es.correct,
                    es.wrong,
                    es.not_answered,
                    es.total_questions,
                    es.attempted_at,
                    pkg_totals.total_exams,
                    pkg_totals.taken_count
                FROM student s
                JOIN class c ON c.id = s.current_class_id
                JOIN student_package_mapping spm
                    ON spm.student_id = s.student_id AND spm.status = 'Active'
                JOIN packages p
                    ON p.id = spm.package_id AND p.deleted = '0'
                JOIN package_question_paper_mapping pqpm
                    ON pqpm.package_id = p.id
                JOIN question_paper qp
                    ON qp.id = pqpm.question_paper_id AND qp.tenant_id = ?
                JOIN question_paper_template qpt
                    ON qpt.question_paper_id = qp.id
                JOIN paper_template pt
                    ON pt.id = qpt.paper_template_id
                    AND pt.class_id = s.current_class_id
                    AND pt.medium   = s.medium
                LEFT JOIN (
                    SELECT
                        pqpm2.package_id,
                        sss.question_paper_id,
                        MIN(sss.id)              AS summary_id,
                        SUM(sss.marks_obtained)  AS marks_obtained,
                        SUM(sss.max_marks)       AS max_marks,
                        SUM(sss.correct)         AS correct,
                        SUM(sss.wrong)           AS wrong,
                        SUM(sss.not_answered)    AS not_answered,
                        SUM(sss.total_questions) AS total_questions,
                        MAX(sss.attempted_at)    AS attempted_at
                    FROM student_subject_summary sss
                    JOIN package_question_paper_mapping pqpm2
                        ON pqpm2.question_paper_id = sss.question_paper_id
                    WHERE sss.tenant_id = ? AND sss.student_user_id = ?
                    GROUP BY pqpm2.package_id, sss.question_paper_id
                ) es ON es.package_id = p.id AND es.question_paper_id = qp.id
                JOIN (
                    SELECT
                        pqpm3.package_id,
                        COUNT(DISTINCT pqpm3.question_paper_id) AS total_exams,
                        COUNT(DISTINCT es2.question_paper_id)   AS taken_count
                    FROM package_question_paper_mapping pqpm3
                    LEFT JOIN (
                        SELECT DISTINCT pqpm4.package_id, sss2.question_paper_id
                        FROM student_subject_summary sss2
                        JOIN package_question_paper_mapping pqpm4
                            ON pqpm4.question_paper_id = sss2.question_paper_id
                        WHERE sss2.tenant_id = ? AND sss2.student_user_id = ?
                    ) es2 ON es2.package_id = pqpm3.package_id
                         AND es2.question_paper_id = pqpm3.question_paper_id
                    GROUP BY pqpm3.package_id
                ) pkg_totals ON pkg_totals.package_id = p.id
                WHERE s.user_id = ?
                ORDER BY p.start_date DESC, qp.question_paper_name ASC
                """;

        List<java.util.Map<String, Object>> rows = jdbcTemplate.queryForList(
                sql,
                tenantId,       // 1  qp.tenant_id
                tenantId,       // 2  es  sss.tenant_id
                studentUserId,  // 3  es  sss.student_user_id
                tenantId,       // 4  pkg_totals sss2.tenant_id
                studentUserId,  // 5  pkg_totals sss2.student_user_id
                studentUserId   // 6  outer WHERE s.user_id
        );

        java.util.Map<Integer, com.mahaexam.common.bean.PackageExamResultDTO> packageMap =
                new java.util.LinkedHashMap<>();

        for (java.util.Map<String, Object> r : rows) {
            int packageId = ((Number) r.get("package_id")).intValue();
            long questionPaperId = ((Number) r.get("question_paper_id")).longValue();

            com.mahaexam.common.bean.PackageExamResultDTO pkg = packageMap.computeIfAbsent(packageId, id ->
                    com.mahaexam.common.bean.PackageExamResultDTO.builder()
                            .packageId(id)
                            .packageName((String) r.get("package_name"))
                            .packageStartDate(r.get("package_start_date") instanceof java.sql.Date
                                    ? ((java.sql.Date) r.get("package_start_date")).toLocalDate() : null)
                            .packageEndDate(r.get("package_end_date") instanceof java.sql.Date
                                    ? ((java.sql.Date) r.get("package_end_date")).toLocalDate() : null)
                            .classId(((Number) r.get("class_id")).intValue())
                            .className((String) r.get("class_name"))
                            .medium((String) r.get("medium"))
                            .totalExams(((Number) r.get("total_exams")).intValue())
                            .takenCount(((Number) r.get("taken_count")).intValue())
                            .exams(new java.util.ArrayList<>())
                            .build());

            java.math.BigDecimal mo = r.get("marks_obtained") != null
                    ? new java.math.BigDecimal(r.get("marks_obtained").toString()) : null;
            java.math.BigDecimal mm = r.get("max_marks") != null
                    ? new java.math.BigDecimal(r.get("max_marks").toString()) : null;
            java.math.BigDecimal pct = null;
            if (mo != null && mm != null && mm.compareTo(java.math.BigDecimal.ZERO) != 0) {
                pct = mo.multiply(new java.math.BigDecimal("100"))
                        .divide(mm, 2, java.math.RoundingMode.HALF_UP);
            }
            boolean isTaken = r.get("is_taken") != null
                    && ((Number) r.get("is_taken")).intValue() == 1;

            com.mahaexam.common.bean.PackageExamResultDTO.ExamResult exam =
                    com.mahaexam.common.bean.PackageExamResultDTO.ExamResult.builder()
                            .questionPaperId(questionPaperId)
                            .questionPaperName((String) r.get("question_paper_name"))
                            .totalDuration(r.get("total_duration") != null
                                    ? ((Number) r.get("total_duration")).intValue() : null)
                            .totalMarks(r.get("total_marks") != null
                                    ? ((Number) r.get("total_marks")).intValue() : null)
                            .summaryId(isTaken && r.get("summary_id") != null
                                    ? ((Number) r.get("summary_id")).longValue() : null)
                            .isTaken(isTaken)
                            .marksObtained(isTaken ? mo : null)
                            .maxMarks(isTaken ? mm : null)
                            .scorePercent(isTaken ? pct : null)
                            .correct(isTaken && r.get("correct") != null
                                    ? ((Number) r.get("correct")).intValue() : null)
                            .wrong(isTaken && r.get("wrong") != null
                                    ? ((Number) r.get("wrong")).intValue() : null)
                            .notAnswered(isTaken && r.get("not_answered") != null
                                    ? ((Number) r.get("not_answered")).intValue() : null)
                            .totalQuestions(isTaken && r.get("total_questions") != null
                                    ? ((Number) r.get("total_questions")).intValue() : null)
                            .attemptedAt(isTaken && r.get("attempted_at") instanceof java.sql.Timestamp
                                    ? ((java.sql.Timestamp) r.get("attempted_at")).toLocalDateTime() : null)
                            .build();

            pkg.getExams().add(exam);
        }

        return new java.util.ArrayList<>(packageMap.values());
    }
}

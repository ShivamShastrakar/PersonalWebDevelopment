package com.mahaexam.tenant.management.repository;

import com.mahaexam.SpringbootAppApplication;
import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.QuestionPaperResponseDTO;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.packagemanagment.model.PackageModel;
import com.mahaexam.tenant.management.bean.*;
import com.mahaexam.tenant.management.model.AcademicYear;
import com.mahaexam.tenant.management.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private static final Logger logger = LogManager.getLogger(StudentRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final AcademicYearRepository academicYearRepository;

    public StudentRepositoryImpl(JdbcTemplate jdbcTemplate, AcademicYearRepository academicYearRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.academicYearRepository = academicYearRepository;
    }


    @Override
    public Student save(Student student) {
        String sql = "INSERT INTO student (user_id, current_class_id, current_subject_group_id, "
                + "target_final_exam_year,parent_id,student_reference_id,medium,school_name,school_address,category,institute_name,parallel_reservation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, student.getUserId());
            ps.setLong(2, student.getCurrentClassId());
            ps.setLong(3, student.getCurrentSubjectGroupId());
            ps.setInt(4, student.getTargetFinalExamYear());
            if (null == student.getParentId()) {
                ps.setNull(5, java.sql.Types.BIGINT);
            } else {
                ps.setLong(5, student.getParentId());
            }
            if(Objects.isNull(student.getStudentReferenceId())){
                ps.setNull(6, java.sql.Types.BIGINT);
            }else {
                ps.setLong(6, student.getStudentReferenceId());
            }
            ps.setString(7, student.getMedium());
            ps.setString(8, student.getSchoolName());
            ps.setString(9, student.getSchoolAddress());
            ps.setString(10, student.getCategory());
            ps.setString(11, student.getInstituteName());
            ps.setString(12, student.getParallelReservation());
            return ps;
        }, keyHolder);

        // Retrieve and set the generated ID
        Number key = keyHolder.getKey();
        if (key != null) {
            student.setStudentId(key.longValue());
        }

        return student;

    }

    @Override
    public Optional<Student> findById(Long studentId) {
        String sql = "SELECT * FROM student WHERE student_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new StudentRowMapper(), studentId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public Optional<StudentDetailsBean> findByIdFull(Long userId) {
        String sql = """
                select distinct s.*,au.*,c.class_name,sg.group_name, a.*, p.*,state.state_name state,t.taluka_name taluka,d.district_name district from student s
                inner join application_user au on s.user_id =au.user_id
                left join class c on c.id = s.current_class_id
                left join student_course sCourse on sCourse.student_id = s.student_id
                left join subject_group sg on sg.group_id = s.current_subject_group_id
                left join subject_group_mapping sgm on sgm.group_id = s.current_subject_group_id
                left join address a on a.address_id = au.address_id
                left join state state on a.state_id = state.id
                left join taluka t on a.taluka_id = t.id
                left join district d on a.district_id = d.id
                left join parent p on p.parent_id = s.parent_id where au.user_id=?
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new StudentDetailsBeanRowMapper(), userId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Student> findAll() {
        String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    @Override
    public PaginatedResponse<StudentDetailsBean> search(UserBean user, StudentSerchBean studentSerchBean) {
        String selectPart = "select distinct s.*,au.*,c.class_name,sg.group_name, a.*, p.*,state.state_name state,t.taluka_name taluka,d.district_name district ";
        String countPart = "SELECT COUNT(DISTINCT s.student_id) ";

        boolean filterByAcademicYear = studentSerchBean.getAcademicYearId() != null;

        String otherPart = """
                from student s
                inner join application_user au on s.user_id =au.user_id
                inner join users u on u.user_id =au.user_id
                left join student_course sCourse on sCourse.student_id = s.student_id
                left join subject_group sg on sg.group_id = s.current_subject_group_id
                left join subject_group_mapping sgm on sgm.group_id = s.current_subject_group_id
                left join address a on a.address_id = au.address_id
                left join class c on c.id = s.current_class_id
                left join state state on a.state_id = state.id
                left join taluka t on a.taluka_id = t.id
                left join district d on a.district_id = d.id
                left join parent p on p.parent_id = s.parent_id
                """ + (filterByAcademicYear ? "inner join academic_year ay on ay.id = ? " : "") + """
                where u.tenant_id = ?  AND au.deleted = '0'
                """;

        StringBuilder sql = new StringBuilder(selectPart);
        sql.append(otherPart);
        List<Object> params = new ArrayList<>();
        if (filterByAcademicYear) {
            params.add(studentSerchBean.getAcademicYearId());
        }
        params.add(user.getTenantId());
        StringBuilder addtionalConditionPart = new StringBuilder();
        if (studentSerchBean.getClassId() != null) {
            addtionalConditionPart.append(" AND  s.current_class_id = ? ");
            params.add(studentSerchBean.getClassId());
        }
        if (studentSerchBean.getStudentReferenceId() != null) {
            addtionalConditionPart.append(" AND  s.student_reference_id = ? ");
            params.add(studentSerchBean.getStudentReferenceId());
        }
        if (filterByAcademicYear) {
            addtionalConditionPart.append(" AND au.created_at >= ay.start_date AND au.created_at <= ay.end_date ");
        }
        if (studentSerchBean.getDays() != null) {
            addtionalConditionPart.append(" AND au.created_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(studentSerchBean.getDays());
        }
        String countSQL = countPart + otherPart + addtionalConditionPart.toString();
        sql.append(addtionalConditionPart);

        Long count = jdbcTemplate.queryForObject(countSQL, Long.class, params.toArray());

        sql.append(" order by au.created_at desc LIMIT ? OFFSET ?");
        int offset = (studentSerchBean.getPage().equals(0)) ? studentSerchBean.getSize() : studentSerchBean.getPage() * studentSerchBean.getSize();
        params.add(offset);
        params.add(studentSerchBean.getPage());
        List<StudentDetailsBean> studentModels = jdbcTemplate.query(sql.toString(), new StudentDetailsBeanRowMapper(), params.toArray());

        int totalPages = (int) Math.ceil((double) count / studentSerchBean.getSize());
        return PaginatedResponse.<StudentDetailsBean>builder()
                .content(studentModels)
                .page(studentSerchBean.getPage())
                .size(studentSerchBean.getSize())
                .totalElements(count)
                .totalPages(totalPages)
                .build();
//		return jdbcTemplate.query(sql, new StudentDetailsBeanRowMapper());
    }

    @Override
    public Student update(Student student) {
        if(Objects.nonNull(student.getParentId()) && student.getParentId()>0){
            String sql = "UPDATE student SET user_id = ?, current_class_id = ?, current_subject_group_id = ?, "
                    + "target_final_exam_year = ?,parent_id=?, medium=?, school_name=?, school_address=?, category=?, institute_name=?, parallel_reservation=? WHERE student_id = ?";

            jdbcTemplate.update(sql, student.getUserId(), student.getCurrentClassId(), student.getCurrentSubjectGroupId(),
                    student.getTargetFinalExamYear(),student.getParentId(), student.getMedium(), student.getSchoolName(), student.getSchoolAddress(), student.getCategory(), student.getInstituteName(), student.getParallelReservation(), student.getStudentId());
        } else{
            String sql = "UPDATE student SET user_id = ?, current_class_id = ?, current_subject_group_id = ?, "
                    + "target_final_exam_year = ?, medium=?, school_name=?, school_address=?, category=?, institute_name=?, parallel_reservation=? WHERE student_id = ?";

            jdbcTemplate.update(sql, student.getUserId(), student.getCurrentClassId(), student.getCurrentSubjectGroupId(),
                    student.getTargetFinalExamYear(), student.getMedium(), student.getSchoolName(), student.getSchoolAddress(), student.getCategory(), student.getInstituteName(), student.getParallelReservation(), student.getStudentId());
        }
        return student;
    }

    @Override
    public void delete(Long studentId) {
        String sql = "DELETE FROM student WHERE student_id = ?";
        jdbcTemplate.update(sql, studentId);
    }

    @Override
    public Optional<Student> findByUserId(Long userId) {
        String sql = """
                SELECT distinct s.*  FROM student s  WHERE s.user_id = ?
                """;
                //"SELECT * FROM student WHERE user_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new StudentRowMapper(), userId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Student> getAllStudentsByRefferalUsreId(Long studentReferralId) {
        String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    @Override
    public Integer getStudentCountRefferedByGivenUserId(Long studentRefferalId) {
        String sql = "SELECT COUNT(*) FROM student WHERE student_reference_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, studentRefferalId);
        return (count != null) ? count : 0;
    }

    @Override
    public Integer getAllStudentsCount(UserBean userbean) {
            String sql = """
                    SELECT COUNT(*) FROM student s
                    inner join application_user au
                    on s.user_id = au.user_id
                    inner join users u
                    on u.user_id = s.user_id
                    where u.tenant_id =? AND au.deleted = '0'
                    """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,userbean.getTenantId());
        return (count != null) ? count : 0;
    }

    @Override
    public Integer getAllStudentsCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        String countPart = "SELECT COUNT(DISTINCT s.student_id) ";
        String queryPart = """
                FROM student s
                   INNER JOIN application_user au ON s.user_id = au.user_id
                   INNER JOIN users u ON au.user_id = u.user_id
                   INNER JOIN academic_year ay
                   LEFT JOIN class c ON c.id = s.current_class_id
                   LEFT JOIN subject_group sg ON sg.group_id = s.current_subject_group_id
                   LEFT JOIN address a ON a.address_id = au.address_id
                   LEFT JOIN state st ON a.state_id = st.id
                   LEFT JOIN taluka t ON a.taluka_id = t.id
                   LEFT JOIN district d ON a.district_id = d.id
                   LEFT JOIN parent p ON p.parent_id = s.parent_id
                   WHERE u.tenant_id = ? AND au.deleted = '0'
                   AND ay.id = ? AND au.created_at >= ay.start_date AND au.created_at <= ay.end_date
                """;

        StringBuilder sqlBuilder = new StringBuilder(queryPart);
        List<Object> params = new ArrayList<>();
        params.add(tenantId);
        params.add(academicYearId);

        if (days != null) {
            sqlBuilder.append(" AND au.created_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        Integer count = jdbcTemplate.queryForObject(countPart + sqlBuilder.toString(), Integer.class, params.toArray());
        return (count != null) ? count : 0;
    }

    @Override
    public boolean hasPackage(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("studentId cannot be null");
        }

        String sql = "SELECT COUNT(*) FROM student_package_mapping spm " +
                "                inner join student s on spm.student_id = s.student_id " +
                "                WHERE s.user_id =? AND status = '" + AppConstants.PACKAGE_STATUS_ACTIVE + "'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        return count != null && count > 0;
    }

    /**
     * Fetches paginated distinct students without an assigned package.
     *
     * @param pageable Pagination details (page number, size, sort - but sort is hardcoded here; extend if needed).
     * @return A Page of Student objects.
     */
    @Override
    public PaginatedResponse<Student> getStudentsWithoutPackage(Pageable pageable) {
        // Count query for total records
        String countSelectSql = """
                SELECT COUNT(DISTINCT s.student_id)
                """;
        String selectSql = " SELECT DISTINCT s.*,au.created_at";
        String dataSql = """
                    FROM student s
                    INNER JOIN application_user au ON s.user_id = au.user_id
                    LEFT JOIN student_package_mapping spm ON spm.student_id = s.student_id
                    WHERE spm.package_id IS NULL
                """;
        String orderByLimitSql = """
                    ORDER BY au.created_at desc
                    LIMIT ? OFFSET ? 
                """;
        long total = jdbcTemplate.queryForObject(countSelectSql + dataSql, Long.class);

        // Data query with pagination (OFFSET = page * size, LIMIT = size)

        int offset = (pageable.getPageNumber() == 0) ? pageable.getPageSize() : pageable.getPageNumber() * pageable.getPageSize();
        List<Student> students = jdbcTemplate.query(selectSql + dataSql + orderByLimitSql, new StudentRowMapper(), pageable.getPageSize(), offset);

        int totalPages = (int) Math.ceil((double) total / students.size());
        return PaginatedResponse.<Student>builder()
                .content(students)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(total)
                .totalPages(totalPages)
                .build();
    }
    @Override
    public Integer getStudentPackageCount(Long userId) {
        String sql = """
                SELECT COUNT(DISTINCT spm.package_id)
                    FROM student s
                    INNER JOIN student_package_mapping spm
                    ON s.student_id = spm.student_id
                    WHERE s.user_id = ?
                    """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,userId);
        return (count != null) ? count : 0;
    }

    @Override
    public List<PackageModel> getStudentPackages(Long student_id) {
        String sql = """
                select p.* 
                from student s
                inner join student_package_mapping spm 
                    on s.student_id = spm.student_id
                inner join packages p 
                    on spm.package_id = p.id
                where s.student_id = ?
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PackageModel.class), student_id);
    }

    @Override
    public List<StudentDetailsBean> getAllStudentsByChannelPartnerId(Long channelPartnerId) {
        String sql = """
                select distinct s.*,au.*,c.class_name,sg.group_name, a.*, p.*,state.state_name state,t.taluka_name taluka,d.district_name district from student s
                inner join application_user au on s.user_id =au.user_id
                inner join class c on c.id = s.current_class_id
                inner join student_course sCourse on sCourse.student_id = s.student_id
                inner join subject_group sg on sg.group_id = s.current_subject_group_id
                inner join subject_group_mapping sgm on sgm.group_id = s.current_subject_group_id
                left join address a on a.address_id = au.address_id
                left join state state on a.state_id = state.id
                left join taluka t on a.taluka_id = t.id
                left join district d on a.district_id = d.id
                left join parent p on p.parent_id = s.parent_id 
                where s.student_reference_id = ? 
                """;
        try {
            return jdbcTemplate.query(sql, new StudentDetailsBeanRowMapper(), channelPartnerId);
        } catch (Exception e) {
            logger.error("Error fetching students by channel partner ID: {}", channelPartnerId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public PaginatedResponse<StudentDetailsBean> getActiveStudentsWithPaidPackage(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable) {
        String selectPart = "SELECT DISTINCT s.*, au.*, c.class_name, sg.group_name, a.*, st.state_name as state, t.taluka_name as taluka, d.district_name as district, p.* ";
        String countPart = "SELECT COUNT(DISTINCT s.student_id) ";
        String queryPart = """
                FROM student s
                   INNER JOIN application_user au ON s.user_id = au.user_id
                   INNER JOIN users u ON au.user_id = u.user_id
                   INNER JOIN student_package_mapping spm ON s.student_id = spm.student_id
                   INNER JOIN packages pk ON spm.package_id = pk.id
                   INNER JOIN academic_year ay
                   LEFT JOIN class c ON c.id = s.current_class_id
                   LEFT JOIN subject_group sg ON sg.group_id = s.current_subject_group_id
                   LEFT JOIN address a ON a.address_id = au.address_id
                   LEFT JOIN state st ON a.state_id = st.id
                   LEFT JOIN taluka t ON a.taluka_id = t.id
                   LEFT JOIN district d ON a.district_id = d.id
                   LEFT JOIN parent p ON p.parent_id = s.parent_id
                   WHERE u.tenant_id = ? AND au.deleted = '0' AND pk.amount > 0 AND spm.status = 'Active'
                   AND ay.id=? and au.created_at>=ay.start_date and au.created_at<=ay.end_date
                """;

        StringBuilder sqlBuilder = new StringBuilder(queryPart);
        List<Object> params = new ArrayList<>();
        params.add(tenantId);
        params.add(academicYearId);

        if (days != null) {
            sqlBuilder.append(" AND au.created_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        Long count = jdbcTemplate.queryForObject(countPart + sqlBuilder.toString(), Long.class, params.toArray());

        sqlBuilder.append(" ORDER BY au.created_at DESC LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add((int) pageable.getOffset());

        List<StudentDetailsBean> students = jdbcTemplate.query(selectPart + sqlBuilder.toString(), new StudentDetailsBeanRowMapper(), params.toArray());

        int totalPages = (int) Math.ceil((double) count / pageable.getPageSize());
        return PaginatedResponse.<StudentDetailsBean>builder()
                .content(students)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(count)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public Integer getActiveStudentsWithPaidPackageCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        String countPart = "SELECT COUNT(DISTINCT s.student_id) ";
        String queryPart = """
                 FROM student s
                   INNER JOIN application_user au ON s.user_id = au.user_id
                   INNER JOIN users u ON au.user_id = u.user_id
                   INNER JOIN student_package_mapping spm ON s.student_id = spm.student_id
                   INNER JOIN packages pk ON spm.package_id = pk.id
                   INNER JOIN academic_year ay
                   LEFT JOIN class c ON c.id = s.current_class_id
                   LEFT JOIN subject_group sg ON sg.group_id = s.current_subject_group_id
                   LEFT JOIN address a ON a.address_id = au.address_id
                   LEFT JOIN state st ON a.state_id = st.id
                   LEFT JOIN taluka t ON a.taluka_id = t.id
                   LEFT JOIN district d ON a.district_id = d.id
                   LEFT JOIN parent p ON p.parent_id = s.parent_id
                   WHERE u.tenant_id = ? AND au.deleted = '0' AND pk.amount > 0 AND spm.status = 'Active'
                   AND ay.id=? and au.created_at>=ay.start_date and au.created_at<=ay.end_date
                """;

        StringBuilder sqlBuilder = new StringBuilder(queryPart);
        List<Object> params = new ArrayList<>();
        params.add(tenantId);
        params.add(academicYearId);

        if (days != null) {
            sqlBuilder.append(" AND au.created_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        Integer count = jdbcTemplate.queryForObject(countPart + sqlBuilder.toString(), Integer.class, params.toArray());
        return (count != null) ? count : 0;
    }

    @Override
    public PaginatedResponse<QuestionPaperResponseDTO> getTotalExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable) {
        String selectPart = "SELECT DISTINCT qp.* ";
        String countPart = "SELECT COUNT(DISTINCT pqpm.question_paper_id, s.student_id) ";
        String queryPart = """
                FROM student s
                INNER JOIN application_user au ON s.user_id = au.user_id
                INNER JOIN users u ON au.user_id = u.user_id
                INNER JOIN student_package_mapping spm
                    ON s.student_id = spm.student_id AND spm.status = 'Active'
                INNER JOIN packages pk
                    ON spm.package_id = pk.id AND pk.deleted = '0'
                INNER JOIN package_question_paper_mapping pqpm
                    ON pqpm.package_id = pk.id
                INNER JOIN question_paper qp
                    ON qp.id = pqpm.question_paper_id
                INNER JOIN academic_year ay
                    ON ay.id = ?
                    AND qp.start_date >= ay.start_date
                    AND qp.end_date <= ay.end_date
                WHERE u.tenant_id = ?
                  AND au.deleted = '0'
                """;

        StringBuilder sqlBuilder = new StringBuilder(queryPart);
        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            //TODO needs to check for this condition as we are checking created_at of question paper here, need to confirm with requirement
            sqlBuilder.append(" AND qp.created_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        Long count = jdbcTemplate.queryForObject(countPart + sqlBuilder.toString(), Long.class, params.toArray());

        sqlBuilder.append(" ORDER BY qp.created_at DESC LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add((int) pageable.getOffset());

        List<QuestionPaperResponseDTO> exams = jdbcTemplate.query(selectPart + sqlBuilder.toString(), (rs, rowNum) -> {
            QuestionPaperResponseDTO dto = new QuestionPaperResponseDTO();
            dto.setId(rs.getLong("id"));
            dto.setQuestionPaperName(rs.getString("question_paper_name"));
            dto.setAcademicYear(rs.getString("academic_year"));
            dto.setStatus(rs.getString("status"));
            dto.setStartDate(rs.getTimestamp("start_date") != null ? rs.getTimestamp("start_date").toLocalDateTime() : null);
            dto.setEndDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date").toLocalDateTime() : null);
            dto.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
            dto.setTenantId(rs.getLong("tenant_id"));
            return dto;
        }, params.toArray());

        int totalPages = (int) Math.ceil((double) count / pageable.getPageSize());
        return PaginatedResponse.<QuestionPaperResponseDTO>builder()
                .content(exams)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(count)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public Integer getTotalExamCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(DISTINCT pqpm.question_paper_id, s.student_id) 
                FROM student s
                INNER JOIN application_user au ON s.user_id = au.user_id
                INNER JOIN users u ON au.user_id = u.user_id
                INNER JOIN student_package_mapping spm ON s.student_id = spm.student_id AND spm.status = 'Active'
                INNER JOIN packages pk ON spm.package_id = pk.id AND pk.deleted = '0'
                INNER JOIN package_question_paper_mapping pqpm ON pqpm.package_id = pk.id
                INNER JOIN question_paper qp ON qp.id = pqpm.question_paper_id
                INNER JOIN academic_year ay
                    ON ay.id = ?
                    AND qp.start_date >= ay.start_date
                    AND qp.end_date <= ay.end_date
                WHERE u.tenant_id = ? AND au.deleted = '0' 
                """);
        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            //TODO needs to check for this condition as we are checking created_at of question paper here, need to confirm with requirement
            sql.append(" AND qp.created_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sql.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, params.toArray());
        return (count != null) ? count : 0;
    }

    @Override
    public Integer getUpcomingExamCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(DISTINCT qp.id)
                FROM question_paper qp
                JOIN package_question_paper_mapping pqpm ON pqpm.question_paper_id = qp.id
                JOIN packages pk ON pk.id = pqpm.package_id AND pk.deleted = '0'
                JOIN student_package_mapping spm ON spm.package_id = pk.id AND spm.status = 'Active'
                JOIN student s ON s.student_id = spm.student_id
                JOIN application_user au ON au.user_id = s.user_id
                JOIN users u ON u.user_id = au.user_id
                JOIN question_paper_template qpt ON qpt.question_paper_id = qp.id
                JOIN paper_template pt ON pt.id = qpt.paper_template_id
                    AND pt.class_id = s.current_class_id
                    AND pt.medium = s.medium
                JOIN academic_year ay
                    ON ay.id = ?
                    AND qp.start_date >= ay.start_date
                    AND qp.end_date <= ay.end_date
                WHERE u.tenant_id = ?
                  AND au.deleted = '0'
                  AND qp.status = 'ACTIVE'
                  AND  NOW()>=qp.start_date and NOW()<=qp.end_date
                """);

        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            sql.append(" AND qp.start_date <= (NOW() + INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sql.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, params.toArray());
        return (count != null) ? count : 0;
    }

    @Override
    public PaginatedResponse<QuestionPaperResponseDTO> getUpcomingExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable) {
        String selectPart = "SELECT DISTINCT qp.* ";
        String countPart = "SELECT COUNT(DISTINCT qp.id) ";
        String queryPart = """
                FROM question_paper qp
                JOIN package_question_paper_mapping pqpm ON pqpm.question_paper_id = qp.id
                JOIN packages pk ON pk.id = pqpm.package_id AND pk.deleted = '0'
                JOIN student_package_mapping spm ON spm.package_id = pk.id AND spm.status = 'Active'
                JOIN student s ON s.student_id = spm.student_id
                JOIN application_user au ON au.user_id = s.user_id
                JOIN users u ON u.user_id = au.user_id
                JOIN question_paper_template qpt ON qpt.question_paper_id = qp.id
                JOIN paper_template pt ON pt.id = qpt.paper_template_id
                    AND pt.class_id = s.current_class_id
                    AND pt.medium = s.medium
                INNER JOIN academic_year ay
                    ON ay.id = ?
                    AND qp.start_date >= ay.start_date
                    AND qp.end_date <= ay.end_date
                WHERE u.tenant_id = ?
                  AND au.deleted = '0'
                  AND qp.status = 'ACTIVE'
                  AND NOW() >= qp.start_date AND NOW() <= qp.end_date
                """;

        StringBuilder sqlBuilder = new StringBuilder(queryPart);
        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            sqlBuilder.append(" AND qp.start_date <= (NOW() + INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        Long count = jdbcTemplate.queryForObject(countPart + sqlBuilder.toString(), Long.class, params.toArray());

        sqlBuilder.append(" ORDER BY qp.start_date ASC LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add((int) pageable.getOffset());

        List<QuestionPaperResponseDTO> exams = jdbcTemplate.query(selectPart + sqlBuilder.toString(), (rs, rowNum) -> {
            QuestionPaperResponseDTO dto = new QuestionPaperResponseDTO();
            dto.setId(rs.getLong("id"));
            dto.setQuestionPaperName(rs.getString("question_paper_name"));
            dto.setAcademicYear(rs.getString("academic_year"));
            dto.setStatus(rs.getString("status"));
            dto.setStartDate(rs.getTimestamp("start_date") != null ? rs.getTimestamp("start_date").toLocalDateTime() : null);
            dto.setEndDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date").toLocalDateTime() : null);
            dto.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
            dto.setTenantId(rs.getLong("tenant_id"));
            return dto;
        }, params.toArray());

        int totalPages = (int) Math.ceil((double) count / pageable.getPageSize());
        return PaginatedResponse.<QuestionPaperResponseDTO>builder()
                .content(exams)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(count)
                .totalPages(totalPages)
                .build();
    }


    @Override
    public PaginatedResponse<CompletedExamDetailsBean> getCompletedExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable) {
        String selectPart = """
                SELECT 
                    qp.id as questionPaperId, qp.question_paper_name, 
                    CONCAT(au.first_name, ' ', au.last_name) as studentName,
                    SUM(sss.marks_obtained) as marksObtained,
                    SUM(sss.max_marks) as maxMarks,
                    MAX(sss.attempted_at) as attemptedAt 
                """;
        String countPart = "SELECT COUNT(DISTINCT sss.question_paper_id, sss.student_user_id) ";
        String queryPart = """
                FROM student_subject_summary sss
                    JOIN question_paper qp ON sss.question_paper_id = qp.id
                    JOIN application_user au ON sss.student_user_id = au.user_id
                    JOIN student s ON s.user_id = au.user_id
                    JOIN student_package_mapping spm ON s.student_id = spm.student_id AND spm.status = 'Active'
                    INNER JOIN academic_year ay
                    	ON ay.id = ?
                    	AND sss.attempted_at >= ay.start_date
                    	AND sss.attempted_at <= ay.end_date
                    WHERE sss.tenant_id = ? AND au.deleted = '0'
                """;

        StringBuilder sqlBuilder = new StringBuilder(queryPart);
        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            sqlBuilder.append(" AND sss.attempted_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        Long count = jdbcTemplate.queryForObject(countPart + sqlBuilder.toString(), Long.class, params.toArray());

        sqlBuilder.append(" GROUP BY qp.id, qp.question_paper_name, au.user_id, au.first_name, au.last_name ");
        sqlBuilder.append(" ORDER BY attemptedAt DESC LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add((int) pageable.getOffset());

        List<CompletedExamDetailsBean> completedExams = jdbcTemplate.query(selectPart + sqlBuilder.toString(), (rs, rowNum) -> {
            java.math.BigDecimal mo = rs.getBigDecimal("marksObtained");
            java.math.BigDecimal mm = rs.getBigDecimal("maxMarks");
            java.math.BigDecimal pct = null;
            if (mo != null && mm != null && mm.compareTo(java.math.BigDecimal.ZERO) != 0) {
                pct = mo.multiply(new java.math.BigDecimal("100"))
                        .divide(mm, 2, java.math.RoundingMode.HALF_UP);
            }
            return CompletedExamDetailsBean.builder()
                    .questionPaperId(rs.getLong("questionPaperId"))
                    .questionPaperName(rs.getString("question_paper_name"))
                    .studentName(rs.getString("studentName"))
                    .marksObtained(mo)
                    .maxMarks(mm)
                    .scorePercent(pct)
                    .attemptedAt(rs.getTimestamp("attemptedAt") != null ? rs.getTimestamp("attemptedAt").toLocalDateTime() : null)
                    .build();
        }, params.toArray());

        int totalPages = (int) Math.ceil((double) count / pageable.getPageSize());
        return PaginatedResponse.<CompletedExamDetailsBean>builder()
                .content(completedExams)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(count)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public Integer getCompletedExamCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(DISTINCT sss.question_paper_id, sss.student_user_id) 
                FROM student_subject_summary sss
                INNER JOIN question_paper qp ON sss.question_paper_id = qp.id
                INNER JOIN application_user au ON sss.student_user_id = au.user_id
                INNER JOIN student s ON s.user_id = au.user_id
                INNER JOIN student_package_mapping spm ON s.student_id = spm.student_id AND spm.status = 'Active'
                INNER JOIN academic_year ay
                    	ON ay.id = ?
                    	AND sss.attempted_at >= ay.start_date
                    	AND sss.attempted_at <= ay.end_date
                WHERE sss.tenant_id = ? AND au.deleted = '0' 
                """);
        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            sql.append(" AND sss.attempted_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }
        if (boardId != null) {
            sql.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, params.toArray());
        return (count != null) ? count : 0;
    }

    @Override
    public List<EnrollmentByExamBean> getStudentEnrollmentByExam(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        String sql = """
                SELECT sg.group_name as examName, COUNT(s.student_id) as studentCount
                FROM student s
                INNER JOIN subject_group sg ON s.current_subject_group_id = sg.group_id
                INNER JOIN application_user au ON s.user_id = au.user_id
                INNER JOIN users u ON au.user_id = u.user_id
                INNER JOIN academic_year ay
                    	ON ay.id = ?
                    	AND au.created_at >= ay.start_date
                    	AND au.created_at <= ay.end_date
                WHERE u.tenant_id = ? AND au.deleted = '0' 
                """;

        StringBuilder sqlBuilder = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            sqlBuilder.append(" AND au.created_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        sqlBuilder.append(" GROUP BY sg.group_name ");

        return jdbcTemplate.query(sqlBuilder.toString(), (rs, rowNum) -> EnrollmentByExamBean.builder()
                .examName(rs.getString("examName"))
                .studentCount(rs.getLong("studentCount"))
                .build(), params.toArray());
    }

    @Override
    public PaginatedResponse<StudentDetailsBean> getStudentEnrollmentByExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, String examName, Pageable pageable) {
        String selectPart = "SELECT DISTINCT s.*, au.*, c.class_name, sg.group_name, a.*, st.state_name as state, t.taluka_name as taluka, d.district_name as district, p.* ";
        String countPart = "SELECT COUNT(DISTINCT s.student_id) ";
        String queryPart = """
                FROM student s
                INNER JOIN application_user au ON s.user_id = au.user_id
                INNER JOIN users u ON au.user_id = u.user_id
                INNER JOIN subject_group sg ON s.current_subject_group_id = sg.group_id
                LEFT JOIN class c ON c.id = s.current_class_id
                LEFT JOIN address a ON a.address_id = au.address_id
                LEFT JOIN state st ON a.state_id = st.id
                LEFT JOIN taluka t ON a.taluka_id = t.id
                LEFT JOIN district d ON a.district_id = d.id
                LEFT JOIN parent p ON p.parent_id = s.parent_id
                INNER JOIN academic_year ay
                    	ON ay.id = ?
                    	AND au.created_at >= ay.start_date
                    	AND au.created_at <= ay.end_date
                WHERE u.tenant_id = ? AND au.deleted = '0' 
                """;

        StringBuilder sqlBuilder = new StringBuilder(queryPart);
        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            sqlBuilder.append(" AND au.created_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        if (examName != null && !examName.isEmpty()) {
            sqlBuilder.append(" AND sg.group_name = ? ");
            params.add(examName);
        }

        Long count = jdbcTemplate.queryForObject(countPart + sqlBuilder.toString(), Long.class, params.toArray());

        sqlBuilder.append(" ORDER BY au.created_at DESC LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add((int) pageable.getOffset());

        List<StudentDetailsBean> students = jdbcTemplate.query(selectPart + sqlBuilder.toString(), new StudentDetailsBeanRowMapper(), params.toArray());

        int totalPages = (int) Math.ceil((double) count / pageable.getPageSize());
        return PaginatedResponse.<StudentDetailsBean>builder()
                .content(students)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(count)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public List<StudentJourneyBean> getStudentJourneyStats(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        String sql = """
                SELECT 
                    pk.package_type as groupName,
                    COUNT(DISTINCT pqpm.question_paper_id, s.student_id) as totalExams,
                    COUNT(DISTINCT sss.question_paper_id, s.student_id) as completedExams
                FROM student s
                JOIN application_user au ON s.user_id = au.user_id
                JOIN users u ON au.user_id = u.user_id
                JOIN student_package_mapping spm ON s.student_id = spm.student_id AND spm.status = 'Active'
                JOIN packages pk ON spm.package_id = pk.id AND pk.deleted = '0'
                JOIN package_question_paper_mapping pqpm ON pqpm.package_id = pk.id
                JOIN question_paper qp ON qp.id = pqpm.question_paper_id
                LEFT JOIN student_subject_summary sss ON sss.question_paper_id = qp.id AND sss.student_user_id = s.user_id
                INNER JOIN academic_year ay
                    	ON ay.id = ?
                    	AND sss.attempted_at >= ay.start_date
                    	AND sss.attempted_at <= ay.end_date
                WHERE u.tenant_id = ? AND au.deleted = '0' 
                """;

        StringBuilder sqlBuilder = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            sqlBuilder.append(" AND sss.attempted_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        sqlBuilder.append(" GROUP BY pk.package_type ");

        return jdbcTemplate.query(sqlBuilder.toString(), (rs, rowNum) -> {
            int total = rs.getInt("totalExams");
            int completed = rs.getInt("completedExams");
            double rate = 0;
            if (total > 0) {
                rate = (double) completed * 100 / total;
            }
            return StudentJourneyBean.builder()
                    .groupName(rs.getString("groupName"))
                    .totalExams(total)
                    .completedExams(completed)
                    .completionRate(rate)
                    .build();
        }, params.toArray());
    }

    @Override
    public PaginatedResponse<CompletedExamDetailsBean> getStudentJourneyDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, String packageType, Pageable pageable) {
        String selectPart = """
                SELECT 
                    qp.id as questionPaperId, qp.question_paper_name, 
                    CONCAT(au.first_name, ' ', au.last_name) as studentName,
                    SUM(sss.marks_obtained) as marksObtained,
                    SUM(sss.max_marks) as maxMarks,
                    MAX(sss.attempted_at) as attemptedAt,
                    pk.package_type
                """;
        String countPart = "SELECT COUNT(DISTINCT sss.question_paper_id, sss.student_user_id) ";
        String queryPart = """
                FROM student_subject_summary sss
                JOIN question_paper qp ON sss.question_paper_id = qp.id
                JOIN application_user au ON sss.student_user_id = au.user_id
                JOIN student s ON s.user_id = au.user_id
                JOIN student_package_mapping spm ON s.student_id = spm.student_id AND spm.status = 'Active'
                JOIN packages pk ON spm.package_id = pk.id AND pk.deleted = '0'
                JOIN package_question_paper_mapping pqpm ON pqpm.package_id = pk.id AND pqpm.question_paper_id = qp.id
                 INNER JOIN academic_year ay
                    	ON ay.id = ?
                    	AND sss.attempted_at >= ay.start_date
                    	AND sss.attempted_at <= ay.end_date
                WHERE sss.tenant_id = ? 
                """;

        StringBuilder sqlBuilder = new StringBuilder(queryPart);
        List<Object> params = new ArrayList<>();
        params.add(academicYearId);
        params.add(tenantId);

        if (days != null) {
            sqlBuilder.append(" AND sss.attempted_at >= (NOW() - INTERVAL ? DAY) ");
            params.add(days);
        }

        if (boardId != null) {
            sqlBuilder.append(" AND s.board_id = ? ");
            params.add(boardId);
        }

        if (packageType != null && !packageType.isEmpty()) {
            sqlBuilder.append(" AND pk.package_type = ? ");
            params.add(packageType);
        }

        Long count = jdbcTemplate.queryForObject(countPart + sqlBuilder.toString(), Long.class, params.toArray());

        sqlBuilder.append(" GROUP BY qp.id, qp.question_paper_name, au.user_id, au.first_name, au.last_name, pk.package_type ");
        sqlBuilder.append(" ORDER BY attemptedAt DESC LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add((int) pageable.getOffset());

        List<CompletedExamDetailsBean> details = jdbcTemplate.query(selectPart + sqlBuilder.toString(), (rs, rowNum) -> {
            java.math.BigDecimal mo = rs.getBigDecimal("marksObtained");
            java.math.BigDecimal mm = rs.getBigDecimal("maxMarks");
            java.math.BigDecimal pct = null;
            if (mo != null && mm != null && mm.compareTo(java.math.BigDecimal.ZERO) != 0) {
                pct = mo.multiply(new java.math.BigDecimal("100"))
                        .divide(mm, 2, java.math.RoundingMode.HALF_UP);
            }
            return CompletedExamDetailsBean.builder()
                    .questionPaperId(rs.getLong("questionPaperId"))
                    .questionPaperName(rs.getString("question_paper_name"))
                    .studentName(rs.getString("studentName"))
                    .marksObtained(mo)
                    .maxMarks(mm)
                    .scorePercent(pct)
                    .attemptedAt(rs.getTimestamp("attemptedAt") != null ? rs.getTimestamp("attemptedAt").toLocalDateTime() : null)
                    .build();
        }, params.toArray());

        int totalPages = (int) Math.ceil((double) count / pageable.getPageSize());
        return PaginatedResponse.<CompletedExamDetailsBean>builder()
                .content(details)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(count)
                .totalPages(totalPages)
                .build();
    }
}

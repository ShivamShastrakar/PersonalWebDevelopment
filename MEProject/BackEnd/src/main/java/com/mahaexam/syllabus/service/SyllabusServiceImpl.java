package com.mahaexam.syllabus.service;

import java.util.List;

import com.mahaexam.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.model.Board;
import com.mahaexam.common.model.ClassEntity;
import com.mahaexam.common.model.Subject;
import com.mahaexam.common.repo.BoardRepository;
import com.mahaexam.common.repo.ClassRepository;
import com.mahaexam.common.repo.SubjectRepository;
import com.mahaexam.papertemplate.bean.CreateSyllabusRequestDTO;
import com.mahaexam.papertemplate.bean.SyllabusChapterDTO;
import com.mahaexam.papertemplate.bean.SyllabusResponseDTO;
import com.mahaexam.papertemplate.model.Syllabus;
import com.mahaexam.papertemplate.model.SyllabusChapter;
import com.mahaexam.syllabus.repository.SyllabusChapterRepository;
import com.mahaexam.syllabus.repository.SyllabusRepository;

@Service
@Transactional
public class SyllabusServiceImpl implements SyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final SyllabusChapterRepository syllabusChapterRepository;
    private final BoardRepository boardRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;

    public SyllabusServiceImpl(
            SyllabusRepository syllabusRepository,
            SyllabusChapterRepository syllabusChapterRepository,
            BoardRepository boardRepository,
            ClassRepository classRepository,
            SubjectRepository subjectRepository) {

        this.syllabusRepository = syllabusRepository;
        this.syllabusChapterRepository = syllabusChapterRepository;
        this.boardRepository = boardRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
    }

    /* -------------------------------------------------
     * CREATE SYLLABUS
     * ------------------------------------------------- */
    @Override
    public SyllabusResponseDTO createSyllabus(
            CreateSyllabusRequestDTO request, Long tenantId, Long userId) {

        boolean exists =
                syllabusRepository
                        .existsByClassIdAndSubjectIdAndBoardIdAndMediumAndAcademicYearAndTenantId(
                                request.getClassId(),
                                request.getSubjectId(),
                                request.getBoardId(),
                                request.getMedium(),
                                request.getAcademicYear(),
                                tenantId
                        );

        if (exists) {
            throw new ValidationException("Syllabus already exists for this board, class, subject, medium and academic year combination");
        }

        // Generate name from BoardName, Medium, ClassName, SubjectName, Academic Year
        String syllabusName = generateSyllabusName(
                request.getBoardId(),
                request.getClassId(),
                request.getSubjectId(),
                request.getMedium(),
                request.getAcademicYear()
        );

        Syllabus syllabus = new Syllabus();
        syllabus.setClassId(request.getClassId());
        syllabus.setSubjectId(request.getSubjectId());
        syllabus.setBoardId(request.getBoardId());
        syllabus.setMedium(request.getMedium());
        syllabus.setAcademicYear(request.getAcademicYear());
        syllabus.setStatus("ACTIVE");
        syllabus.setTenantId(tenantId);
        syllabus.setCreatedBy(userId);
        syllabus.setUpdatedBy(userId);
        syllabus.setName(syllabusName);

        Syllabus saved = syllabusRepository.save(syllabus);

        for (SyllabusChapterDTO dto : request.getChapters()) {
            SyllabusChapter sc = new SyllabusChapter();
            sc.setSyllabusId(saved.getId());
            sc.setChapterId(dto.getChapterId());
            sc.setNumberOfQuestions(dto.getNumberOfQuestions());
            sc.setMarks(dto.getMarks());
            sc.setCoveragePercentage(dto.getCoveragePercentage());
            syllabusChapterRepository.save(sc);
        }

        SyllabusResponseDTO response = new SyllabusResponseDTO();
        response.setSyllabusId(saved.getId());
        response.setStatus(saved.getStatus());
        return response;
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

    /* -------------------------------------------------
     * GET SYLLABUS
     * ------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public Syllabus getSyllabus(
            Long classId,
            Long subjectId,
            String medium,
            Integer academicYear,
            Long tenantId) {

        Syllabus syllabus = syllabusRepository
                .findByClassIdAndSubjectIdAndMediumAndAcademicYearAndTenantId(
                        classId, subjectId, medium, academicYear, tenantId
                )
                .orElseThrow(() ->
                        new RuntimeException("Syllabus not found")
                );

        // Fetch and populate syllabus chapters
        List<SyllabusChapter> chapters = syllabusChapterRepository.findBySyllabusId(syllabus.getId());
        syllabus.setChapters(chapters);

        return syllabus;
    }

    /* -------------------------------------------------
     * CHECK EXISTS
     * ------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public boolean syllabusExists(
            Long classId,
            Long subjectId,
            Long boardId,
            String medium,
            Integer academicYear,
            Long tenantId) {

        return syllabusRepository
                .existsByClassIdAndSubjectIdAndBoardIdAndMediumAndAcademicYearAndTenantId(
                        classId, subjectId, boardId, medium, academicYear, tenantId
                );
    }

    /* -------------------------------------------------
     * GET SYLLABUS BY ID
     * ------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public Syllabus getSyllabusById(Long id) {
        Syllabus syllabus = syllabusRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Syllabus not found with id: " + id)
                );

        // Fetch and populate syllabus chapters
        List<SyllabusChapter> chapters = syllabusChapterRepository.findBySyllabusId(syllabus.getId());
        syllabus.setChapters(chapters);

        return syllabus;
    }

    /* -------------------------------------------------
     * UPDATE SYLLABUS
     * ------------------------------------------------- */
    @Override
    public void updateSyllabus(Syllabus syllabus) {
        // Check if syllabus exists
        syllabusRepository
                .findById(syllabus.getId())
                .orElseThrow(() ->
                        new RuntimeException("Syllabus not found with id: " + syllabus.getId())
                );
        
        syllabus.setStatus("ACTIVE");
        syllabusRepository.update(syllabus);
    }

    /* -------------------------------------------------
     * GET ALL SYLLABI WITH OPTIONAL FILTERS
     * ------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public List<Syllabus> getAllSyllabi(Long tenantId, String status) {
        return syllabusRepository.findAll(tenantId, status);
    }
    
    @Override
    public int deleteSyllabus(int id) {
        return syllabusRepository.softDelete(id);
    }
}

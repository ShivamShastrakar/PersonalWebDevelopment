package com.mahaexam.tenant.management.service;

import com.mahaexam.common.model.Board;
import com.mahaexam.common.repo.BoardRepository;
import com.mahaexam.tenant.management.model.AcademicYear;
import com.mahaexam.tenant.management.repository.AcademicYearRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;
    private final BoardRepository boardRepository;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository, BoardRepository boardRepository) {
        this.academicYearRepository = academicYearRepository;
        this.boardRepository = boardRepository;
    }

    private void populateDefaultDates(AcademicYear ay) {
        if (ay.getBoardId() == null || (ay.getStartDate() != null && ay.getEndDate() != null)) return;
        Board board = boardRepository.findById(ay.getBoardId());
        if (board == null) return;

        int startYear;
        try {
            startYear = Integer.parseInt(ay.getName().split("-")[0].trim());
        } catch (Exception e) {
            LocalDate now = LocalDate.now();
            startYear = now.getYear();
        }

        String boardName = board.getBoardName().toUpperCase();
        if (boardName.contains("CBSE") || boardName.contains("ICSE")) {
            if (ay.getStartDate() == null) ay.setStartDate(LocalDate.of(startYear, 4, 1));
            if (ay.getEndDate() == null) ay.setEndDate(LocalDate.of(startYear + 1, 3, 31));
        } else {
            if (ay.getStartDate() == null) ay.setStartDate(LocalDate.of(startYear, 6, 1));
            if (ay.getEndDate() == null) ay.setEndDate(LocalDate.of(startYear + 1, 4, 30));
        }
    }

    private void checkDuplicateName(AcademicYear academicYear, Long excludeId) {
        Optional<AcademicYear> existing;
        if (academicYear.getBoardId() != null) {
            existing = academicYearRepository.findByNameTenantAndBoardId(
                    academicYear.getName(), academicYear.getTenantId(), academicYear.getBoardId());
        } else {
            existing = academicYearRepository.findByNameAndTenantId(
                    academicYear.getName(), academicYear.getTenantId());
        }
        if (existing.isPresent() && !existing.get().getId().equals(excludeId)) {
            throw new IllegalArgumentException(
                    "Academic year with name '" + academicYear.getName() + "' already exists for this board and tenant .");
        }
    }

    @Override
    public AcademicYear save(AcademicYear academicYear) {
        checkDuplicateName(academicYear, null);
        populateDefaultDates(academicYear);
        return academicYearRepository.save(academicYear);
    }

    @Override
    public Optional<AcademicYear> findById(Long id) {
        return academicYearRepository.findById(id);
    }

    @Override
    public Optional<AcademicYear> findByNameAndTenantId(String name, Long tenantId) {
        return academicYearRepository.findByNameAndTenantId(name, tenantId);
    }

    @Override
    public Optional<AcademicYear> findByNameTenantAndBoardId(String name, Long tenantId, Integer boardId) {
        return academicYearRepository.findByNameTenantAndBoardId(name, tenantId, boardId);
    }

    @Override
    public List<AcademicYear> findAllByTenantId(Long tenantId) {
        return academicYearRepository.findAllByTenantId(tenantId);
    }

    @Override
    public AcademicYear update(AcademicYear academicYear) {
        checkDuplicateName(academicYear, academicYear.getId());
        populateDefaultDates(academicYear);
        return academicYearRepository.update(academicYear);
    }

    @Override
    public void delete(Long id) {
        academicYearRepository.delete(id);
    }
}



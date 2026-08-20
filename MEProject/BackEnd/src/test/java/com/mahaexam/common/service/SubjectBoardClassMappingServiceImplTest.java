package com.mahaexam.common.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.SubjectBoardClassMapping;
import com.mahaexam.common.repo.SubjectBoardClassMappingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubjectBoardClassMappingServiceImplTest {

    @Mock
    private SubjectBoardClassMappingRepository repository;

    @InjectMocks
    private SubjectBoardClassMappingServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMapping_shouldSave_whenNotExists() {
        SubjectBoardClassMapping mapping = SubjectBoardClassMapping.builder()
                .subjectId(1)
                .classId(2)
                .boardId(3)
                .build();

        when(repository.existsBySubjectClassBoard(1, 2, 3, null)).thenReturn(false);
        when(repository.save(mapping)).thenReturn(1);

        int result = service.createMapping(mapping);

        assertEquals(1, result);
        verify(repository).existsBySubjectClassBoard(1, 2, 3, null);
        verify(repository).save(mapping);
    }

    @Test
    void createMapping_shouldThrow_whenAlreadyExists() {
        SubjectBoardClassMapping mapping = SubjectBoardClassMapping.builder()
                .subjectId(1)
                .classId(2)
                .boardId(3)
                .build();

        when(repository.existsBySubjectClassBoard(1, 2, 3, null)).thenReturn(true);

        ValidationException ex = assertThrows(ValidationException.class,
                () -> service.createMapping(mapping));
        assertEquals("This subject-class-board-medium combination already exists.", ex.getMessage());

        verify(repository).existsBySubjectClassBoard(1, 2, 3, null);
        verify(repository, never()).save(any(SubjectBoardClassMapping.class));
    }

    @Test
    void deleteMapping_shouldReturnDeletedCount() {
        when(repository.softDelete(5)).thenReturn(1);
        int result = service.deleteMapping(5);
        assertEquals(1, result);
        verify(repository).softDelete(5);
    }

    @Test
    void getAllMappings_shouldReturnList() {
        List<SubjectBoardClassMapping> list = List.of(
                SubjectBoardClassMapping.builder().id(1).build(),
                SubjectBoardClassMapping.builder().id(2).build()
        );

        when(repository.findAll()).thenReturn(list);

        List<SubjectBoardClassMapping> result = service.getAllMappings();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        verify(repository).findAll();
    }

    @Test
    void getMappingById_shouldReturnOptional() {
        SubjectBoardClassMapping mapping = SubjectBoardClassMapping.builder().id(99).build();

        when(repository.findById(99)).thenReturn(Optional.of(mapping));

        Optional<SubjectBoardClassMapping> result = service.getMappingById(99);

        assertTrue(result.isPresent());
        assertEquals(99, result.get().getId());
        verify(repository).findById(99);
    }

    @Test
    void saveMappingsForSubjects_shouldReturnTotalSaved() {
        List<SubjectBoardClassMapping> mappings = List.of(
                SubjectBoardClassMapping.builder().subjectId(1).classId(2).boardId(3).build(),
                SubjectBoardClassMapping.builder().subjectId(4).classId(5).boardId(6).build()
        );

        when(repository.save(anyList())).thenReturn(new int[]{1, 1});

        int result = service.saveMappingsForSubjects(mappings);

        assertEquals(2, result);
        verify(repository).save(anyList());
    }
}
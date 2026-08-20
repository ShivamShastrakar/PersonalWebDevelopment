package com.mahaexam.common.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Subject;
import com.mahaexam.common.repo.SubjectRepository;
import com.mahaexam.common.repo.SubjectBoardClassMappingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SubjectBoardClassMappingRepository subjectBoardClassMappingRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSubjectsByTenant_shouldReturnSubjects() {
        Long tenantId = 1L;
        List<Subject> subjects = List.of(
                Subject.builder().subjectId(1).subjectName("Physics").tenantId(tenantId).subjectBoardClassMappings(new java.util.ArrayList<>()).build(),
                Subject.builder().subjectId(2).subjectName("Chemistry").tenantId(tenantId).subjectBoardClassMappings(new java.util.ArrayList<>()).build()
        );

        when(subjectRepository.findAllByTenant(tenantId)).thenReturn(subjects);
        when(subjectBoardClassMappingRepository.findByIds(anyList())).thenReturn(new java.util.ArrayList<>());

        List<Subject> result = subjectService.getAllSubjectsByTenant(tenantId);

        assertEquals(2, result.size());
        verify(subjectRepository).findAllByTenant(tenantId);
        verify(subjectBoardClassMappingRepository).findByIds(anyList());
    }

    @Test
    void getSubjectById_shouldReturnSubject() {
        int id = 100;
        Subject subject = Subject.builder()
                .subjectId(id)
                .subjectName("Mathematics")
                .tenantId(1L)
                .subjectBoardClassMappings(new java.util.ArrayList<>())
                .build();

        when(subjectRepository.findById(id)).thenReturn(subject);
        when(subjectBoardClassMappingRepository.findByIds(anyList())).thenReturn(new java.util.ArrayList<>());

        Subject result = subjectService.getSubjectById(id);

        assertEquals("Mathematics", result.getSubjectName());
        verify(subjectRepository).findById(id);
        verify(subjectBoardClassMappingRepository).findByIds(anyList());
    }

    @Test
    void createSubject_shouldThrowExceptionWhenSubjectNameExists() {
        Subject subject = Subject.builder()
                .subjectId(0)
                .subjectName("Biology")
                .tenantId(1L)
                .build();

        when(subjectRepository.existsBySubjectNameAndTenantId("Biology", 1L)).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            subjectService.createSubject(subject);
        });

        assertEquals("Subject name already exists for this tenant.", exception.getMessage());
        verify(subjectRepository).existsBySubjectNameAndTenantId("Biology", 1L);
    }

    @Test
    void createSubject_shouldSaveSuccessfully() {
        Subject subject = Subject.builder()
                .subjectId(1)
                .subjectName("Physics")
                .tenantId(1L)
                .subjectBoardClassMappings(new java.util.ArrayList<>())
                .build();

        when(subjectRepository.existsBySubjectNameAndTenantId(anyString(), anyLong())).thenReturn(false);
        when(subjectRepository.save(any(Subject.class))).thenReturn(1);
        when(subjectBoardClassMappingRepository.save(anyList())).thenReturn(new int[]{1});

        int result = subjectService.createSubject(subject);

        assertEquals(1, result);
        verify(subjectRepository).save(any(Subject.class));
        verify(subjectBoardClassMappingRepository).save(anyList());
    }

    @Test
    void updateSubject_shouldThrowExceptionWhenSubjectNameAlreadyExists() {
        Subject subject = Subject.builder()
                .subjectId(10)
                .subjectName("Geography")
                .tenantId(1L)
                .build();

        when(subjectRepository.existsBySubjectNameAndTenantIdExceptId("Geography", 1L, 10)).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            subjectService.updateSubject(subject);
        });

        assertEquals("Subject name already exists for this tenant.", exception.getMessage());
        verify(subjectRepository).existsBySubjectNameAndTenantIdExceptId("Geography", 1L, 10);
    }

    @Test
    void updateSubject_shouldUpdateSuccessfully() {
        Subject subject = Subject.builder()
                .subjectId(1)
                .subjectName("Physics")
                .tenantId(1L)
                .subjectBoardClassMappings(new java.util.ArrayList<>())
                .build();

        when(subjectRepository.existsBySubjectNameAndTenantIdExceptId(anyString(), anyLong(), anyInt())).thenReturn(false);
        when(subjectBoardClassMappingRepository.deleteBySubjectId(anyInt())).thenReturn(1);
        when(subjectBoardClassMappingRepository.save(anyList())).thenReturn(new int[]{1});
        when(subjectRepository.update(any(Subject.class))).thenReturn(1);

        int result = subjectService.updateSubject(subject);

        assertEquals(1, result);
        verify(subjectBoardClassMappingRepository).deleteBySubjectId(anyInt());
        verify(subjectBoardClassMappingRepository).save(anyList());
        verify(subjectRepository).update(any(Subject.class));
    }

    @Test
    void deleteSubject_shouldCallSoftDelete() {
        int id = 45;
        when(subjectRepository.softDelete(id)).thenReturn(1);

        int result = subjectService.deleteSubject(id);

        assertEquals(1, result);
        verify(subjectRepository).softDelete(id);
    }
}
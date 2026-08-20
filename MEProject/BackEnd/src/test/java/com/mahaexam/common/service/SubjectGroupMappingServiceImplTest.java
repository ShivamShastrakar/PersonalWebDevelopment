package com.mahaexam.common.service;

import com.mahaexam.common.model.SubjectGroupMapping;
import com.mahaexam.common.repo.SubjectGroupMappingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectGroupMappingServiceImplTest {

    @Mock
    private SubjectGroupMappingRepository repository;

    @InjectMocks
    private SubjectGroupMappingServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMapping_shouldSaveSuccessfully() {
        SubjectGroupMapping mapping = SubjectGroupMapping.builder()
                .groupId(10)
                .subjectId(20)
                .build();

        when(repository.save(mapping)).thenReturn(1);

        int result = service.createMapping(mapping);

        assertEquals(1, result);
        verify(repository).save(mapping);
    }

    @Test
    void deleteMapping_shouldReturnDeleteCount() {
        when(repository.softDelete(5)).thenReturn(1);

        int result = service.deleteMapping(5);

        assertEquals(1, result);
        verify(repository).softDelete(5);
    }

    @Test
    void getAllMappings_shouldReturnList() {
        List<SubjectGroupMapping> list = List.of(
                SubjectGroupMapping.builder().groupId(1).subjectId(2).build(),
                SubjectGroupMapping.builder().groupId(3).subjectId(4).build()
        );

        when(repository.findAll()).thenReturn(list);

        List<SubjectGroupMapping> result = service.getAllMappings();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void getMappingById_shouldReturnMapping() {
        SubjectGroupMapping mapping = SubjectGroupMapping.builder()
                .groupId(11)
                .subjectId(22)
                .build();

        when(repository.findById(100)).thenReturn(mapping);

        SubjectGroupMapping result = service.getMappingById(100);

        assertEquals(11, result.getGroupId());
        assertEquals(22, result.getSubjectId());
        verify(repository).findById(100);
    }

    @Test
    void saveGroupWithSubjects_shouldSaveMultipleMappings() {
        int groupId = 50;
        List<Integer> subjectIds = List.of(101, 102, 103);

        when(repository.save(any(SubjectGroupMapping.class))).thenReturn(1);

        int result = service.saveGroupWithSubjects(groupId, subjectIds);

        assertEquals(3, result);
        verify(repository, times(3)).save(any(SubjectGroupMapping.class));
    }
}
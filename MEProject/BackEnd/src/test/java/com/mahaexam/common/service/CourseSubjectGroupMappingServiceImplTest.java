package com.mahaexam.common.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mahaexam.common.model.CourseSubjectGroupMapping;
import com.mahaexam.common.repo.CourseSubjectGroupMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CourseSubjectGroupMappingServiceImplTest {

    @Mock
    private CourseSubjectGroupMappingRepository repository;

    @InjectMocks
    private CourseSubjectGroupMappingServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMappingsForCourse_ShouldDeleteOldAndSaveNewMappings() {
        // Arrange
        int courseId = 101;
        List<Long> subjectGroupIds = new ArrayList<>();
        subjectGroupIds.add(1L);
        subjectGroupIds.add(2L);
        subjectGroupIds.add(3L);

        when(repository.save(any(CourseSubjectGroupMapping.class))).thenReturn(1);

        // Act
        int result = service.saveMappingsForCourse(courseId, subjectGroupIds);

        // Assert
        assertEquals(3, result); // 3 subject group mappings should be saved

        verify(repository).deleteByCourseId(courseId);
        verify(repository, times(3)).save(any(CourseSubjectGroupMapping.class));

        // Capture what is being saved
        ArgumentCaptor<CourseSubjectGroupMapping> captor = ArgumentCaptor.forClass(CourseSubjectGroupMapping.class);
        verify(repository, times(3)).save(captor.capture());

        List<CourseSubjectGroupMapping> capturedMappings = captor.getAllValues();
        for (int i = 0; i < subjectGroupIds.size(); i++) {
            assertEquals(courseId, capturedMappings.get(i).getCourseId());
//            assertEquals(subjectGroupIds.get(i), capturedMappings.get(i).getSubjectGroupId());
        }
    }
}
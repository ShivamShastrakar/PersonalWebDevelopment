package com.mahaexam.common.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Course;
import com.mahaexam.common.repo.CourseClassMappingRepository;
import com.mahaexam.common.repo.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseClassMappingRepository courseClassMappingRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        course = Course.builder()
                .id(1)
                .courseName("Mathematics")
                .tenantId(100L)
                .build();
    }

    @Test
    void testGetAllCoursesByTenant() {
        List<Course> expectedCourses = List.of(course);

        when(courseRepository.findAllByTenant(100L)).thenReturn(expectedCourses);

        List<Course> actualCourses = courseService.getAllCoursesByTenant(100L);

        assertEquals(expectedCourses, actualCourses);
        verify(courseRepository).findAllByTenant(100L);
    }

    @Test
    void testGetCourseById() {
        when(courseRepository.findById(1)).thenReturn(course);
        when(courseClassMappingRepository.findClassIdsByCourseId(1)).thenReturn(List.of(10, 20));

        Course actual = courseService.getCourseById(1);

        assertEquals(course, actual);
        assertEquals(List.of(10, 20), actual.getClassIds());
        verify(courseRepository).findById(1);
        verify(courseClassMappingRepository).findClassIdsByCourseId(1);
    }

    @Test
    void testUpdateCourseSuccess() {
        when(courseRepository.existsByCourseNameAndTenantIdExceptId("Mathematics", 100L, 1)).thenReturn(false);
        when(courseRepository.update(course)).thenReturn(1);
        doNothing().when(courseClassMappingRepository).deleteMappingsForCourse(1);

        int result = courseService.updateCourse(course);

        assertEquals(1, result);
        verify(courseRepository).update(course);
        verify(courseClassMappingRepository).deleteMappingsForCourse(1);
    }

    @Test
    void testUpdateCourse_ThrowsValidationException() {
        when(courseRepository.existsByCourseNameAndTenantIdExceptId("Mathematics", 100L, 1)).thenReturn(true);

        assertThrows(ValidationException.class, () -> courseService.updateCourse(course));
        verify(courseRepository, never()).update(any());
    }

    @Test
    void testDeleteCourse() {
        when(courseRepository.softDelete(1)).thenReturn(1);

        int result = courseService.deleteCourse(1);

        assertEquals(1, result);
        verify(courseRepository).softDelete(1);
    }
}
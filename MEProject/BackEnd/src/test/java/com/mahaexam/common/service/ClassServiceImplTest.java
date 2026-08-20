package com.mahaexam.common.service;

import com.mahaexam.common.bean.ClassesDeleteBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.ClassEntity;
import com.mahaexam.common.repo.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassServiceImplTest {

    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private ClassServiceImpl classService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClassesByTenant() {
        LocalDateTime now = LocalDateTime.now();

        List<ClassEntity> mockClasses = List.of(
                ClassEntity.builder()
                        .id(1)
                        .tenantId(1L)
                        .className("Class 10")
                        .createdAt(now)
                        .updatedAt(now)
                        .deleted("0")
                        .build(),
                ClassEntity.builder()
                        .id(2)
                        .tenantId(1L)
                        .className("Class 12")
                        .createdAt(now)
                        .updatedAt(now)
                        .deleted("0")
                        .build()
        );

        when(classRepository.findAllByTenant(1L)).thenReturn(mockClasses);

        List<ClassEntity> result = classService.getAllClassesByTenant(1L);

        assertEquals(2, result.size());
        verify(classRepository).findAllByTenant(1L);
    }

    @Test
    void testGetClassById() {
        LocalDateTime now = LocalDateTime.now();

        ClassEntity clazz = ClassEntity.builder()
                .id(1)
                .tenantId(1L)
                .className("Class 10")
                .createdAt(now)
                .updatedAt(now)
                .deleted("0")
                .build();

        when(classRepository.findById(1)).thenReturn(clazz);

        ClassEntity result = classService.getClassById(1);

        assertEquals("Class 10", result.getClassName());
        verify(classRepository).findById(1);
    }

    @Test
    void testCreateClass_Success() {
        ClassEntity newClass = ClassEntity.builder()
                .tenantId(1L)
                .className("Class 10")
                .deleted("0")
                .build();

        when(classRepository.existsByClassNameAndTenantId("Class 10", 1L)).thenReturn(false);
        when(classRepository.save(newClass)).thenReturn(1);

        int id = classService.createClass(newClass);

        assertEquals(1, id);
        verify(classRepository).save(newClass);
    }

    @Test
    void testCreateClass_ThrowsValidationException() {
        ClassEntity newClass = ClassEntity.builder()
                .tenantId(1L)
                .className("Class 10")
                .deleted("0")
                .build();

        when(classRepository.existsByClassNameAndTenantId("Class 10", 1L)).thenReturn(true);

        assertThrows(ValidationException.class, () -> classService.createClass(newClass));
        verify(classRepository, never()).save(any());
    }

    @Test
    void testUpdateClass_Success() {
        ClassEntity clazz = ClassEntity.builder()
                .id(1)
                .tenantId(1L)
                .className("Class 10")
                .deleted("0")
                .build();

        when(classRepository.existsByClassNameAndTenantIdExceptId("Class 10", 1L, 1)).thenReturn(false);
        when(classRepository.update(clazz)).thenReturn(1);

        int result = classService.updateClass(clazz);

        assertEquals(1, result);
        verify(classRepository).update(clazz);
    }

    @Test
    void testUpdateClass_ThrowsValidationException() {
        ClassEntity clazz = ClassEntity.builder()
                .id(1)
                .tenantId(1L)
                .className("Class 10")
                .deleted("0")
                .build();

        when(classRepository.existsByClassNameAndTenantIdExceptId("Class 10", 1L, 1)).thenReturn(true);

        assertThrows(ValidationException.class, () -> classService.updateClass(clazz));
        verify(classRepository, never()).update(any());
    }

    @Test
    void testDeleteClass() {
        ClassesDeleteBean deleteBean = new ClassesDeleteBean();

        int[] expectedResult = {1};
        when(classRepository.softDelete(deleteBean)).thenReturn(expectedResult);

        int[] result = classService.deleteClass(deleteBean);

        assertArrayEquals(expectedResult, result);
        verify(classRepository).softDelete(deleteBean);
    }
}
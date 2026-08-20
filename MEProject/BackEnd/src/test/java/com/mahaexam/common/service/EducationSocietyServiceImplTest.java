package com.mahaexam.common.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.mahaexam.common.bean.EducationSocietyBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.EducationSociety;
import com.mahaexam.common.repo.EducationSocietyRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class EducationSocietyServiceImplTest {

    @Mock
    private EducationSocietyRepository repository;

    @InjectMocks
    private EducationSocietyServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSociety_Success() {
        // Arrange
        EducationSocietyBean bean = EducationSocietyBean.builder()
                .societyName("Maha Society")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.existsBySocietyName("Maha Society")).thenReturn(false);
        when(repository.save(any(EducationSociety.class))).thenReturn(1);

        // Act
        int result = service.createSociety(bean);

        // Assert
        assertEquals(1, result);
        verify(repository).save(any(EducationSociety.class));
    }

    @Test
    void testCreateSociety_ThrowsValidationException_WhenDuplicate() {
        // Arrange
        EducationSocietyBean bean = EducationSocietyBean.builder()
                .societyName("Maha Society")
                .build();

        when(repository.existsBySocietyName("Maha Society")).thenReturn(true);

        // Act & Assert
        ValidationException ex = assertThrows(ValidationException.class, () -> {
            service.createSociety(bean);
        });

        assertEquals("Society name already exists.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void testUpdateSociety_Success() {
        // Arrange
        EducationSocietyBean bean = EducationSocietyBean.builder()
                .societyName("Updated Society")
                .build();

        when(repository.existsBySocietyNameExceptId("Updated Society", 1)).thenReturn(false);
        when(repository.update(any(EducationSociety.class))).thenReturn(1);

        // Act
        int result = service.updateSociety(1, bean);

        // Assert
        assertEquals(1, result);
        verify(repository).update(any(EducationSociety.class));
    }

    @Test
    void testUpdateSociety_ThrowsValidationException_WhenDuplicateName() {
        // Arrange
        EducationSocietyBean bean = EducationSocietyBean.builder()
                .societyName("Duplicate")
                .build();

        when(repository.existsBySocietyNameExceptId("Duplicate", 1)).thenReturn(true);

        // Act & Assert
        ValidationException ex = assertThrows(ValidationException.class, () -> {
            service.updateSociety(1, bean);
        });

        assertEquals("Society name already exists.", ex.getMessage());
        verify(repository, never()).update(any());
    }

    @Test
    void testGetSocietyById() {
        EducationSociety mockEntity = EducationSociety.builder().id(1).societyName("XYZ").build();
        when(repository.findById(1)).thenReturn(mockEntity);

        EducationSociety result = service.getSocietyById(1);

        assertEquals("XYZ", result.getSocietyName());
    }

    @Test
    void testGetAllSocieties() {
        List<EducationSociety> mockList = Arrays.asList(
                EducationSociety.builder().id(1).societyName("Soc1").build(),
                EducationSociety.builder().id(2).societyName("Soc2").build()
        );

        when(repository.findAll()).thenReturn(mockList);

        List<EducationSociety> result = service.getAllSocieties();

        assertEquals(2, result.size());
        assertEquals("Soc1", result.get(0).getSocietyName());
    }
}
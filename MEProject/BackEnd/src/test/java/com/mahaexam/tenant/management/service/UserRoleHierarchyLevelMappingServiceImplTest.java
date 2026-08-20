package com.mahaexam.tenant.management.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;
import com.mahaexam.tenant.management.repository.UserRoleHierarchyLevelMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleHierarchyLevelMappingServiceImplTest {

    @Mock
    private UserRoleHierarchyLevelMappingRepository mappingRepository;

    @InjectMocks
    private UserRoleHierarchyLevelMappingServiceImpl mappingService;

    private UserRoleHierarchyLevelMapping testMapping;

    @BeforeEach
    public void setUp() {
        testMapping = UserRoleHierarchyLevelMapping.builder()
                .id(1)
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void testSaveMapping_Success() {
        UserRoleHierarchyLevelMapping mappingToSave = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .build();

        when(mappingRepository.findByUserRoleIdAndHierarchyLevelId(100L, 1)).thenReturn(Optional.empty());
        when(mappingRepository.save(any(UserRoleHierarchyLevelMapping.class))).thenReturn(testMapping);

        UserRoleHierarchyLevelMapping result = mappingService.save(mappingToSave);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(100L, result.getUserRoleId());
        assertEquals(1, result.getUserHierarchyLevelId());
        verify(mappingRepository, times(1)).save(any(UserRoleHierarchyLevelMapping.class));
    }

    @Test
    public void testSaveMapping_NullMapping_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.save(null);
        });
    }

    @Test
    public void testSaveMapping_NullUserRoleId_ThrowsException() {
        UserRoleHierarchyLevelMapping mappingToSave = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(null)
                .userHierarchyLevelId(1)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.save(mappingToSave);
        });
    }

    @Test
    public void testSaveMapping_InvalidUserRoleId_ThrowsException() {
        UserRoleHierarchyLevelMapping mappingToSave = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(-1L)
                .userHierarchyLevelId(1)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.save(mappingToSave);
        });
    }

    @Test
    public void testSaveMapping_NullUserHierarchyLevelId_ThrowsException() {
        UserRoleHierarchyLevelMapping mappingToSave = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(100L)
                .userHierarchyLevelId(null)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.save(mappingToSave);
        });
    }

    @Test
    public void testSaveMapping_InvalidUserHierarchyLevelId_ThrowsException() {
        UserRoleHierarchyLevelMapping mappingToSave = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(100L)
                .userHierarchyLevelId(-1)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.save(mappingToSave);
        });
    }

    @Test
    public void testSaveMapping_DuplicateMapping_ThrowsException() {
        UserRoleHierarchyLevelMapping mappingToSave = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .build();

        when(mappingRepository.findByUserRoleIdAndHierarchyLevelId(100L, 1)).thenReturn(Optional.of(testMapping));

        assertThrows(ValidationException.class, () -> {
            mappingService.save(mappingToSave);
        });
    }

    @Test
    public void testFindById_Success() {
        when(mappingRepository.findById(1)).thenReturn(Optional.of(testMapping));

        Optional<UserRoleHierarchyLevelMapping> result = mappingService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getUserRoleId());
        assertEquals(1, result.get().getUserHierarchyLevelId());
        verify(mappingRepository, times(1)).findById(1);
    }

    @Test
    public void testFindById_NotFound() {
        when(mappingRepository.findById(999)).thenReturn(Optional.empty());

        Optional<UserRoleHierarchyLevelMapping> result = mappingService.findById(999);

        assertFalse(result.isPresent());
        verify(mappingRepository, times(1)).findById(999);
    }

    @Test
    public void testFindById_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.findById(null);
        });
    }

    @Test
    public void testUpdateMapping_Success() {
        UserRoleHierarchyLevelMapping updateMapping = UserRoleHierarchyLevelMapping.builder()
                .id(1)
                .userRoleId(101L)
                .userHierarchyLevelId(2)
                .build();

        when(mappingRepository.existsById(1)).thenReturn(true);
        when(mappingRepository.findByUserRoleIdAndHierarchyLevelId(101L, 2)).thenReturn(Optional.of(updateMapping));
        when(mappingRepository.update(any(UserRoleHierarchyLevelMapping.class))).thenReturn(updateMapping);

        UserRoleHierarchyLevelMapping result = mappingService.update(updateMapping);

        assertNotNull(result);
        assertEquals(101L, result.getUserRoleId());
        assertEquals(2, result.getUserHierarchyLevelId());
        verify(mappingRepository, times(1)).update(any(UserRoleHierarchyLevelMapping.class));
    }

    @Test
    public void testUpdateMapping_NotFound_ThrowsException() {
        UserRoleHierarchyLevelMapping updateMapping = UserRoleHierarchyLevelMapping.builder()
                .id(999)
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .build();

        when(mappingRepository.existsById(999)).thenReturn(false);

        assertThrows(ValidationException.class, () -> {
            mappingService.update(updateMapping);
        });
    }

    @Test
    public void testUpdateMapping_NullId_ThrowsException() {
        UserRoleHierarchyLevelMapping updateMapping = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.update(updateMapping);
        });
    }

    @Test
    public void testDeleteMapping_Success() {
        when(mappingRepository.existsById(1)).thenReturn(true);
        doNothing().when(mappingRepository).delete(1);

        assertDoesNotThrow(() -> {
            mappingService.delete(1);
        });

        verify(mappingRepository, times(1)).delete(1);
    }

    @Test
    public void testDeleteMapping_NotFound_ThrowsException() {
        when(mappingRepository.existsById(999)).thenReturn(false);

        assertThrows(ValidationException.class, () -> {
            mappingService.delete(999);
        });
    }

    @Test
    public void testDeleteMapping_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.delete(null);
        });
    }

    @Test
    public void testFindAll_Success() {
        UserRoleHierarchyLevelMapping mapping2 = UserRoleHierarchyLevelMapping.builder()
                .id(2)
                .userRoleId(101L)
                .userHierarchyLevelId(2)
                .build();

        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(testMapping, mapping2);

        when(mappingRepository.findAll()).thenReturn(mappings);

        List<UserRoleHierarchyLevelMapping> result = mappingService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(100L, result.get(0).getUserRoleId());
        assertEquals(101L, result.get(1).getUserRoleId());
        verify(mappingRepository, times(1)).findAll();
    }

    @Test
    public void testFindAll_Empty() {
        when(mappingRepository.findAll()).thenReturn(Arrays.asList());

        List<UserRoleHierarchyLevelMapping> result = mappingService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(mappingRepository, times(1)).findAll();
    }

    @Test
    public void testFindByUserRoleId_Success() {
        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(testMapping);

        when(mappingRepository.findByUserRoleId(100L)).thenReturn(mappings);

        List<UserRoleHierarchyLevelMapping> result = mappingService.findByUserRoleId(100L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserRoleId());
        verify(mappingRepository, times(1)).findByUserRoleId(100L);
    }

    @Test
    public void testFindByUserRoleId_Empty() {
        when(mappingRepository.findByUserRoleId(999L)).thenReturn(Arrays.asList());

        List<UserRoleHierarchyLevelMapping> result = mappingService.findByUserRoleId(999L);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(mappingRepository, times(1)).findByUserRoleId(999L);
    }

    @Test
    public void testFindByUserRoleId_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.findByUserRoleId(null);
        });
    }

    @Test
    public void testFindByUserHierarchyLevelId_Success() {
        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(testMapping);

        when(mappingRepository.findByUserHierarchyLevelId(1)).thenReturn(mappings);

        List<UserRoleHierarchyLevelMapping> result = mappingService.findByUserHierarchyLevelId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getUserHierarchyLevelId());
        verify(mappingRepository, times(1)).findByUserHierarchyLevelId(1);
    }

    @Test
    public void testFindByUserHierarchyLevelId_Empty() {
        when(mappingRepository.findByUserHierarchyLevelId(999)).thenReturn(Arrays.asList());

        List<UserRoleHierarchyLevelMapping> result = mappingService.findByUserHierarchyLevelId(999);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(mappingRepository, times(1)).findByUserHierarchyLevelId(999);
    }

    @Test
    public void testFindByUserHierarchyLevelId_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.findByUserHierarchyLevelId(null);
        });
    }

    @Test
    public void testFindByUserRoleIdAndHierarchyLevelId_Success() {
        when(mappingRepository.findByUserRoleIdAndHierarchyLevelId(100L, 1)).thenReturn(Optional.of(testMapping));

        Optional<UserRoleHierarchyLevelMapping> result = mappingService.findByUserRoleIdAndHierarchyLevelId(100L, 1);

        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getUserRoleId());
        assertEquals(1, result.get().getUserHierarchyLevelId());
        verify(mappingRepository, times(1)).findByUserRoleIdAndHierarchyLevelId(100L, 1);
    }

    @Test
    public void testFindByUserRoleIdAndHierarchyLevelId_NotFound() {
        when(mappingRepository.findByUserRoleIdAndHierarchyLevelId(999L, 999)).thenReturn(Optional.empty());

        Optional<UserRoleHierarchyLevelMapping> result = mappingService.findByUserRoleIdAndHierarchyLevelId(999L, 999);

        assertFalse(result.isPresent());
        verify(mappingRepository, times(1)).findByUserRoleIdAndHierarchyLevelId(999L, 999);
    }

    @Test
    public void testFindByUserRoleIdAndHierarchyLevelId_NullUserRoleId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.findByUserRoleIdAndHierarchyLevelId(null, 1);
        });
    }

    @Test
    public void testFindByUserRoleIdAndHierarchyLevelId_NullHierarchyLevelId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mappingService.findByUserRoleIdAndHierarchyLevelId(100L, null);
        });
    }

    @Test
    public void testExistsById_True() {
        when(mappingRepository.existsById(1)).thenReturn(true);

        boolean result = mappingService.existsById(1);

        assertTrue(result);
        verify(mappingRepository, times(1)).existsById(1);
    }

    @Test
    public void testExistsById_False() {
        when(mappingRepository.existsById(999)).thenReturn(false);

        boolean result = mappingService.existsById(999);

        assertFalse(result);
        verify(mappingRepository, times(1)).existsById(999);
    }

    @Test
    public void testExistsById_NullId_ReturnsFalse() {
        boolean result = mappingService.existsById(null);

        assertFalse(result);
        verify(mappingRepository, never()).existsById(anyInt());
    }
}

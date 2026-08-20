package com.mahaexam.tenant.management.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserHierarchyLevel;
import com.mahaexam.tenant.management.repository.UserHierarchyLevelRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserHierarchyLevelServiceImplTest {

    @Mock
    private UserHierarchyLevelRepository userHierarchyLevelRepository;

    @InjectMocks
    private UserHierarchyLevelServiceImpl userHierarchyLevelService;

    private UserHierarchyLevel testLevel;

    @BeforeEach
    public void setUp() {
        testLevel = UserHierarchyLevel.builder()
                .id(1)
                .levelName("Executive")
                .description("Executive level users")
                .levelOrder(1)
                .tenantId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void testSaveUserHierarchyLevel_Success() {
        UserHierarchyLevel levelToSave = UserHierarchyLevel.builder()
                .levelName("Manager")
                .description("Manager level users")
                .levelOrder(2)
                .tenantId(1L)
                .build();

        when(userHierarchyLevelRepository.findByLevelName(anyString())).thenReturn(Optional.empty());
        when(userHierarchyLevelRepository.save(any(UserHierarchyLevel.class))).thenReturn(testLevel);

        UserHierarchyLevel result = userHierarchyLevelService.save(levelToSave);

        assertNotNull(result);
        assertEquals("Executive", result.getLevelName());
        assertEquals(1, result.getId());
        verify(userHierarchyLevelRepository, times(1)).save(any(UserHierarchyLevel.class));
    }

    @Test
    public void testSaveUserHierarchyLevel_NullLevel_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.save(null);
        });
    }

    @Test
    public void testSaveUserHierarchyLevel_NullLevelName_ThrowsException() {
        UserHierarchyLevel levelToSave = UserHierarchyLevel.builder()
                .levelName(null)
                .levelOrder(2)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.save(levelToSave);
        });
    }

    @Test
    public void testSaveUserHierarchyLevel_EmptyLevelName_ThrowsException() {
        UserHierarchyLevel levelToSave = UserHierarchyLevel.builder()
                .levelName("   ")
                .levelOrder(2)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.save(levelToSave);
        });
    }

    @Test
    public void testSaveUserHierarchyLevel_NullLevelOrder_ThrowsException() {
        UserHierarchyLevel levelToSave = UserHierarchyLevel.builder()
                .levelName("Manager")
                .levelOrder(null)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.save(levelToSave);
        });
    }

    @Test
    public void testSaveUserHierarchyLevel_NullTenantId_ThrowsException() {
        UserHierarchyLevel levelToSave = UserHierarchyLevel.builder()
                .levelName("Manager")
                .levelOrder(2)
                .tenantId(null)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.save(levelToSave);
        });
    }

    @Test
    public void testSaveUserHierarchyLevel_InvalidTenantId_ThrowsException() {
        UserHierarchyLevel levelToSave = UserHierarchyLevel.builder()
                .levelName("Manager")
                .levelOrder(2)
                .tenantId(0L)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.save(levelToSave);
        });
    }

    @Test
    public void testSaveUserHierarchyLevel_DuplicateLevelName_ThrowsException() {
        UserHierarchyLevel levelToSave = UserHierarchyLevel.builder()
                .levelName("Executive")
                .levelOrder(2)
                .tenantId(1L)
                .build();

        when(userHierarchyLevelRepository.findByLevelName("Executive")).thenReturn(Optional.of(testLevel));

        assertThrows(ValidationException.class, () -> {
            userHierarchyLevelService.save(levelToSave);
        });
    }

    @Test
    public void testFindById_Success() {
        when(userHierarchyLevelRepository.findById(1)).thenReturn(Optional.of(testLevel));

        Optional<UserHierarchyLevel> result = userHierarchyLevelService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Executive", result.get().getLevelName());
        verify(userHierarchyLevelRepository, times(1)).findById(1);
    }

    @Test
    public void testFindById_NotFound() {
        when(userHierarchyLevelRepository.findById(999)).thenReturn(Optional.empty());

        Optional<UserHierarchyLevel> result = userHierarchyLevelService.findById(999);

        assertFalse(result.isPresent());
        verify(userHierarchyLevelRepository, times(1)).findById(999);
    }

    @Test
    public void testFindById_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.findById(null);
        });
    }

    @Test
    public void testFindByLevelName_Success() {
        when(userHierarchyLevelRepository.findByLevelName("Executive")).thenReturn(Optional.of(testLevel));

        Optional<UserHierarchyLevel> result = userHierarchyLevelService.findByLevelName("Executive");

        assertTrue(result.isPresent());
        assertEquals("Executive", result.get().getLevelName());
        verify(userHierarchyLevelRepository, times(1)).findByLevelName("Executive");
    }

    @Test
    public void testFindByLevelName_NotFound() {
        when(userHierarchyLevelRepository.findByLevelName("NonExistent")).thenReturn(Optional.empty());

        Optional<UserHierarchyLevel> result = userHierarchyLevelService.findByLevelName("NonExistent");

        assertFalse(result.isPresent());
        verify(userHierarchyLevelRepository, times(1)).findByLevelName("NonExistent");
    }

    @Test
    public void testFindByLevelName_NullName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.findByLevelName(null);
        });
    }

    @Test
    public void testFindByLevelName_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.findByLevelName("   ");
        });
    }

    @Test
    public void testUpdateUserHierarchyLevel_Success() {
        UserHierarchyLevel updateLevel = UserHierarchyLevel.builder()
                .id(1)
                .levelName("Senior Executive")
                .description("Updated description")
                .levelOrder(1)
                .tenantId(1L)
                .build();

        when(userHierarchyLevelRepository.existsById(1)).thenReturn(true);
        when(userHierarchyLevelRepository.findByLevelName("Senior Executive")).thenReturn(Optional.of(updateLevel));
        when(userHierarchyLevelRepository.update(any(UserHierarchyLevel.class))).thenReturn(updateLevel);

        UserHierarchyLevel result = userHierarchyLevelService.update(updateLevel);

        assertNotNull(result);
        assertEquals("Senior Executive", result.getLevelName());
        verify(userHierarchyLevelRepository, times(1)).update(any(UserHierarchyLevel.class));
    }

    @Test
    public void testUpdateUserHierarchyLevel_NotFound_ThrowsException() {
        UserHierarchyLevel updateLevel = UserHierarchyLevel.builder()
                .id(999)
                .levelName("Manager")
                .levelOrder(2)
                .tenantId(1L)
                .build();

        when(userHierarchyLevelRepository.existsById(999)).thenReturn(false);

        assertThrows(ValidationException.class, () -> {
            userHierarchyLevelService.update(updateLevel);
        });
    }

    @Test
    public void testUpdateUserHierarchyLevel_NullId_ThrowsException() {
        UserHierarchyLevel updateLevel = UserHierarchyLevel.builder()
                .levelName("Manager")
                .levelOrder(2)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.update(updateLevel);
        });
    }

    @Test
    public void testDeleteUserHierarchyLevel_Success() {
        when(userHierarchyLevelRepository.existsById(1)).thenReturn(true);
        doNothing().when(userHierarchyLevelRepository).delete(1);

        assertDoesNotThrow(() -> {
            userHierarchyLevelService.delete(1);
        });

        verify(userHierarchyLevelRepository, times(1)).delete(1);
    }

    @Test
    public void testDeleteUserHierarchyLevel_NotFound_ThrowsException() {
        when(userHierarchyLevelRepository.existsById(999)).thenReturn(false);

        assertThrows(ValidationException.class, () -> {
            userHierarchyLevelService.delete(999);
        });
    }

    @Test
    public void testDeleteUserHierarchyLevel_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userHierarchyLevelService.delete(null);
        });
    }

    @Test
    public void testFindAll_Success() {
        UserHierarchyLevel level2 = UserHierarchyLevel.builder()
                .id(2)
                .levelName("Manager")
                .description("Manager level")
                .levelOrder(2)
                .build();

        List<UserHierarchyLevel> levels = Arrays.asList(testLevel, level2);

        when(userHierarchyLevelRepository.findAll()).thenReturn(levels);

        List<UserHierarchyLevel> result = userHierarchyLevelService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Executive", result.get(0).getLevelName());
        assertEquals("Manager", result.get(1).getLevelName());
        verify(userHierarchyLevelRepository, times(1)).findAll();
    }

    @Test
    public void testFindAll_Empty() {
        when(userHierarchyLevelRepository.findAll()).thenReturn(Arrays.asList());

        List<UserHierarchyLevel> result = userHierarchyLevelService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(userHierarchyLevelRepository, times(1)).findAll();
    }

    @Test
    public void testExistsById_True() {
        when(userHierarchyLevelRepository.existsById(1)).thenReturn(true);

        boolean result = userHierarchyLevelService.existsById(1);

        assertTrue(result);
        verify(userHierarchyLevelRepository, times(1)).existsById(1);
    }

    @Test
    public void testExistsById_False() {
        when(userHierarchyLevelRepository.existsById(999)).thenReturn(false);

        boolean result = userHierarchyLevelService.existsById(999);

        assertFalse(result);
        verify(userHierarchyLevelRepository, times(1)).existsById(999);
    }

    @Test
    public void testExistsById_NullId_ReturnsFalse() {
        boolean result = userHierarchyLevelService.existsById(null);

        assertFalse(result);
        verify(userHierarchyLevelRepository, never()).existsById(anyInt());
    }
}

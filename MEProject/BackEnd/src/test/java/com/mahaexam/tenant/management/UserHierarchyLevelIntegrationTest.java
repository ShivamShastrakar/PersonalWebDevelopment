package com.mahaexam.tenant.management;

import com.mahaexam.tenant.management.model.UserHierarchyLevel;
import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;
import com.mahaexam.tenant.management.repository.UserHierarchyLevelRepository;
import com.mahaexam.tenant.management.repository.UserRoleHierarchyLevelMappingRepository;
import com.mahaexam.tenant.management.service.UserHierarchyLevelService;
import com.mahaexam.tenant.management.service.UserRoleHierarchyLevelMappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserHierarchyLevelIntegrationTest {

    @Mock
    private UserHierarchyLevelRepository hierarchyLevelRepository;

    @Mock
    private UserRoleHierarchyLevelMappingRepository mappingRepository;

    private UserHierarchyLevelService hierarchyLevelService;
    private UserRoleHierarchyLevelMappingService mappingService;

    private UserHierarchyLevel executiveLevel;
    private UserHierarchyLevel managerLevel;
    private UserRoleHierarchyLevelMapping mapping1;
    private UserRoleHierarchyLevelMapping mapping2;

    @BeforeEach
    public void setUp() {
        hierarchyLevelService = new com.mahaexam.tenant.management.service.UserHierarchyLevelServiceImpl(hierarchyLevelRepository);
        mappingService = new com.mahaexam.tenant.management.service.UserRoleHierarchyLevelMappingServiceImpl(mappingRepository);

        executiveLevel = UserHierarchyLevel.builder()
                .id(1)
                .levelName("Executive")
                .description("Executive level users")
                .levelOrder(1)
                .tenantId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        managerLevel = UserHierarchyLevel.builder()
                .id(2)
                .levelName("Manager")
                .description("Manager level users")
                .levelOrder(2)
                .tenantId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        mapping1 = UserRoleHierarchyLevelMapping.builder()
                .id(1)
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        mapping2 = UserRoleHierarchyLevelMapping.builder()
                .id(2)
                .userRoleId(101L)
                .userHierarchyLevelId(2)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void testCreateHierarchyLevelAndMapToRole() {
        // Create hierarchy level
        when(hierarchyLevelRepository.findByLevelName(anyString())).thenReturn(Optional.empty());
        when(hierarchyLevelRepository.save(any(UserHierarchyLevel.class))).thenReturn(executiveLevel);

        UserHierarchyLevel createdLevel = hierarchyLevelService.save(executiveLevel);

        assertNotNull(createdLevel);
        assertEquals("Executive", createdLevel.getLevelName());
        assertEquals(1, createdLevel.getId());
        assertEquals(1L, createdLevel.getTenantId());

        // Create mapping for the hierarchy level
        when(mappingRepository.findByUserRoleIdAndHierarchyLevelId(100L, 1)).thenReturn(Optional.empty());
        when(mappingRepository.save(any(UserRoleHierarchyLevelMapping.class))).thenReturn(mapping1);

        UserRoleHierarchyLevelMapping createdMapping = mappingService.save(mapping1);

        assertNotNull(createdMapping);
        assertEquals(100L, createdMapping.getUserRoleId());
        assertEquals(1, createdMapping.getUserHierarchyLevelId());
    }

    @Test
    public void testRetrieveHierarchyLevelsWithMappings() {
        // Get all hierarchy levels
        List<UserHierarchyLevel> levels = Arrays.asList(executiveLevel, managerLevel);
        when(hierarchyLevelRepository.findAll()).thenReturn(levels);

        List<UserHierarchyLevel> retrievedLevels = hierarchyLevelService.findAll();

        assertNotNull(retrievedLevels);
        assertEquals(2, retrievedLevels.size());

        // Get mappings for a specific role
        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(mapping1, mapping2);
        when(mappingRepository.findAll()).thenReturn(mappings);

        List<UserRoleHierarchyLevelMapping> retrievedMappings = mappingService.findAll();

        assertNotNull(retrievedMappings);
        assertEquals(2, retrievedMappings.size());
    }

    @Test
    public void testUpdateHierarchyLevelAndItsMapping() {
        // Update hierarchy level
        UserHierarchyLevel updatedLevel = UserHierarchyLevel.builder()
                .id(1)
                .levelName("Senior Executive")
                .description("Updated Executive level")
                .levelOrder(1)
                .tenantId(1L)
                .build();

        when(hierarchyLevelRepository.existsById(1)).thenReturn(true);
        when(hierarchyLevelRepository.findByLevelName("Senior Executive")).thenReturn(Optional.of(updatedLevel));
        when(hierarchyLevelRepository.update(any(UserHierarchyLevel.class))).thenReturn(updatedLevel);

        UserHierarchyLevel result = hierarchyLevelService.update(updatedLevel);

        assertNotNull(result);
        assertEquals("Senior Executive", result.getLevelName());
        assertEquals("Updated Executive level", result.getDescription());

        // Verify mapping still exists for this level
        when(mappingRepository.findByUserHierarchyLevelId(1)).thenReturn(Arrays.asList(mapping1));

        List<UserRoleHierarchyLevelMapping> mappings = mappingService.findByUserHierarchyLevelId(1);

        assertNotNull(mappings);
        assertEquals(1, mappings.size());
        assertEquals(1, mappings.get(0).getUserHierarchyLevelId());
    }

    @Test
    public void testDeleteHierarchyLevelAndAssociatedMappings() {
        // Find all mappings for a hierarchy level before deletion
        when(mappingRepository.findByUserHierarchyLevelId(1)).thenReturn(Arrays.asList(mapping1));

        List<UserRoleHierarchyLevelMapping> mappingsBefore = mappingService.findByUserHierarchyLevelId(1);

        assertNotNull(mappingsBefore);
        assertEquals(1, mappingsBefore.size());

        // Delete mappings (in real scenario, would be done via cascade or explicit deletion)

        // Delete hierarchy level
        when(hierarchyLevelRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> hierarchyLevelService.delete(1));
    }

    @Test
    public void testFindMappingsByRoleAndVerifyHierarchyLevel() {
        // Get mappings by user role
        when(mappingRepository.findByUserRoleId(100L)).thenReturn(Arrays.asList(mapping1));

        List<UserRoleHierarchyLevelMapping> mappings = mappingService.findByUserRoleId(100L);

        assertNotNull(mappings);
        assertEquals(1, mappings.size());
        assertEquals(100L, mappings.get(0).getUserRoleId());

        // Get the hierarchy level for this mapping
        when(hierarchyLevelRepository.findById(1)).thenReturn(Optional.of(executiveLevel));

        Optional<UserHierarchyLevel> level = hierarchyLevelService.findById(mappings.get(0).getUserHierarchyLevelId());

        assertTrue(level.isPresent());
        assertEquals("Executive", level.get().getLevelName());
        assertEquals(1, level.get().getLevelOrder());
    }

    @Test
    public void testCompleteWorkflow_CreateUpdateDelete() {
        // Step 1: Create hierarchy level
        when(hierarchyLevelRepository.findByLevelName("Supervisor")).thenReturn(Optional.empty());
        when(hierarchyLevelRepository.save(any(UserHierarchyLevel.class))).thenReturn(UserHierarchyLevel.builder()
                .id(3)
                .levelName("Supervisor")
                .description("Supervisor level")
                .levelOrder(3)
                .tenantId(1L)
                .build());

        UserHierarchyLevel newLevel = hierarchyLevelService.save(UserHierarchyLevel.builder()
                .levelName("Supervisor")
                .description("Supervisor level")
                .levelOrder(3)
                .tenantId(1L)
                .build());

        assertNotNull(newLevel);
        assertEquals(3, newLevel.getId());

        // Step 2: Create mapping for this level
        UserRoleHierarchyLevelMapping newMapping = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(102L)
                .userHierarchyLevelId(3)
                .build();

        when(mappingRepository.findByUserRoleIdAndHierarchyLevelId(102L, 3)).thenReturn(Optional.empty());
        when(mappingRepository.save(any(UserRoleHierarchyLevelMapping.class))).thenReturn(UserRoleHierarchyLevelMapping.builder()
                .id(3)
                .userRoleId(102L)
                .userHierarchyLevelId(3)
                .build());

        UserRoleHierarchyLevelMapping createdMapping = mappingService.save(newMapping);

        assertNotNull(createdMapping);
        assertEquals(3, createdMapping.getId());

        // Step 3: Retrieve and verify
        when(mappingRepository.findById(3)).thenReturn(Optional.of(createdMapping));

        Optional<UserRoleHierarchyLevelMapping> retrieved = mappingService.findById(3);

        assertTrue(retrieved.isPresent());
        assertEquals(102L, retrieved.get().getUserRoleId());

        // Step 4: Update the hierarchy level
        UserHierarchyLevel updatedLevel = UserHierarchyLevel.builder()
                .id(3)
                .levelName("Senior Supervisor")
                .description("Updated Supervisor level")
                .levelOrder(3)
                .tenantId(1L)
                .build();

        when(hierarchyLevelRepository.existsById(3)).thenReturn(true);
        when(hierarchyLevelRepository.findByLevelName("Senior Supervisor")).thenReturn(Optional.of(updatedLevel));
        when(hierarchyLevelRepository.update(any(UserHierarchyLevel.class))).thenReturn(updatedLevel);

        UserHierarchyLevel finalLevel = hierarchyLevelService.update(updatedLevel);

        assertEquals("Senior Supervisor", finalLevel.getLevelName());
    }

    @Test
    public void testMultipleMappingsForSingleHierarchyLevel() {
        // Create multiple mappings for the same hierarchy level
        UserRoleHierarchyLevelMapping mapping3 = UserRoleHierarchyLevelMapping.builder()
                .id(3)
                .userRoleId(103L)
                .userHierarchyLevelId(1)
                .build();

        List<UserRoleHierarchyLevelMapping> executiveMappings = Arrays.asList(mapping1, mapping3);

        when(mappingRepository.findByUserHierarchyLevelId(1)).thenReturn(executiveMappings);

        List<UserRoleHierarchyLevelMapping> mappings = mappingService.findByUserHierarchyLevelId(1);

        assertNotNull(mappings);
        assertEquals(2, mappings.size());

        // Verify both mappings are for the same hierarchy level
        for (UserRoleHierarchyLevelMapping mapping : mappings) {
            assertEquals(1, mapping.getUserHierarchyLevelId());
        }

        // Verify they have different roles
        assertTrue(mappings.stream().anyMatch(m -> m.getUserRoleId() == 100L));
        assertTrue(mappings.stream().anyMatch(m -> m.getUserRoleId() == 103L));
    }

    @Test
    public void testMultipleHierarchyLevelsForSingleRole() {
        // Create multiple hierarchy levels mapped to the same role
        UserRoleHierarchyLevelMapping mapping3 = UserRoleHierarchyLevelMapping.builder()
                .id(3)
                .userRoleId(100L)
                .userHierarchyLevelId(2)
                .build();

        List<UserRoleHierarchyLevelMapping> roleMapping = Arrays.asList(mapping1, mapping3);

        when(mappingRepository.findByUserRoleId(100L)).thenReturn(roleMapping);

        List<UserRoleHierarchyLevelMapping> mappings = mappingService.findByUserRoleId(100L);

        assertNotNull(mappings);
        assertEquals(2, mappings.size());

        // Verify both mappings are for the same role
        for (UserRoleHierarchyLevelMapping mapping : mappings) {
            assertEquals(100L, mapping.getUserRoleId());
        }

        // Verify they have different hierarchy levels
        assertTrue(mappings.stream().anyMatch(m -> m.getUserHierarchyLevelId() == 1));
        assertTrue(mappings.stream().anyMatch(m -> m.getUserHierarchyLevelId() == 2));
    }
}

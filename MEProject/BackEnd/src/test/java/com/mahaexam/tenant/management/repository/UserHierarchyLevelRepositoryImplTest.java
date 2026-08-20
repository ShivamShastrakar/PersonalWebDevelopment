package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserHierarchyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserHierarchyLevelRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserHierarchyLevelRepositoryImpl repository;

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
    public void testSave_Success() {
        UserHierarchyLevel levelToSave = UserHierarchyLevel.builder()
                .levelName("Manager")
                .description("Manager level")
                .levelOrder(2)
                .tenantId(1L)
                .build();

        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);

        UserHierarchyLevel result = repository.save(levelToSave);

        assertNotNull(result);
        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    public void testFindById_Success() {
        when(jdbcTemplate.queryForObject(anyString(), any(UserHierarchyLevelRowMapper.class), eq(1)))
                .thenReturn(testLevel);

        Optional<UserHierarchyLevel> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Executive", result.get().getLevelName());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(UserHierarchyLevelRowMapper.class), eq(1));
    }

    @Test
    public void testFindById_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(UserHierarchyLevelRowMapper.class), eq(999)))
                .thenThrow(new RuntimeException("No data found"));

        Optional<UserHierarchyLevel> result = repository.findById(999);

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindByLevelName_Success() {
        when(jdbcTemplate.queryForObject(anyString(), any(UserHierarchyLevelRowMapper.class), eq("Executive")))
                .thenReturn(testLevel);

        Optional<UserHierarchyLevel> result = repository.findByLevelName("Executive");

        assertTrue(result.isPresent());
        assertEquals("Executive", result.get().getLevelName());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(UserHierarchyLevelRowMapper.class), eq("Executive"));
    }

    @Test
    public void testFindByLevelName_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(UserHierarchyLevelRowMapper.class), eq("NonExistent")))
                .thenThrow(new RuntimeException("No data found"));

        Optional<UserHierarchyLevel> result = repository.findByLevelName("NonExistent");

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindByTenantId_Success() {
        UserHierarchyLevel level2 = UserHierarchyLevel.builder()
                .id(2)
                .levelName("Manager")
                .levelOrder(2)
                .tenantId(1L)
                .build();

        List<UserHierarchyLevel> levels = Arrays.asList(testLevel, level2);

        when(jdbcTemplate.query(anyString(), any(UserHierarchyLevelRowMapper.class), eq(1L)))
                .thenReturn(levels);

        List<UserHierarchyLevel> result = repository.findByTenantId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserHierarchyLevelRowMapper.class), eq(1L));
    }

    @Test
    public void testFindByTenantId_NoResults() {
        when(jdbcTemplate.query(anyString(), any(UserHierarchyLevelRowMapper.class), eq(999L)))
                .thenReturn(Arrays.asList());

        List<UserHierarchyLevel> result = repository.findByTenantId(999L);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserHierarchyLevelRowMapper.class), eq(999L));
    }

    @Test
    public void testUpdate_Success() {
        UserHierarchyLevel updateLevel = UserHierarchyLevel.builder()
                .id(1)
                .levelName("Senior Executive")
                .description("Updated")
                .levelOrder(1)
                .tenantId(1L)
                .build();

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any())).thenReturn(1);

        UserHierarchyLevel result = repository.update(updateLevel);

        assertNotNull(result);
        assertEquals("Senior Executive", result.getLevelName());
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any());
    }

    @Test
    public void testDelete_Success() {
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(1);

        assertDoesNotThrow(() -> repository.delete(1));

        verify(jdbcTemplate, times(1)).update(anyString(), eq(1));
    }

    @Test
    public void testFindAll_Success() {
        UserHierarchyLevel level2 = UserHierarchyLevel.builder()
                .id(2)
                .levelName("Manager")
                .levelOrder(2)
                .build();

        List<UserHierarchyLevel> levels = Arrays.asList(testLevel, level2);

        when(jdbcTemplate.query(anyString(), any(UserHierarchyLevelRowMapper.class)))
                .thenReturn(levels);

        List<UserHierarchyLevel> result = repository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserHierarchyLevelRowMapper.class));
    }

    @Test
    public void testFindAll_Empty() {
        when(jdbcTemplate.query(anyString(), any(UserHierarchyLevelRowMapper.class)))
                .thenReturn(Arrays.asList());

        List<UserHierarchyLevel> result = repository.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testExistsById_True() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(1)))
                .thenReturn(1);

        boolean result = repository.existsById(1);

        assertTrue(result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class), eq(1));
    }

    @Test
    public void testExistsById_False() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(999)))
                .thenReturn(0);

        boolean result = repository.existsById(999);

        assertFalse(result);
    }
}

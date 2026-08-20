package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;
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
public class UserRoleHierarchyLevelMappingRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRoleHierarchyLevelMappingRepositoryImpl repository;

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
    public void testSave_Success() {
        UserRoleHierarchyLevelMapping mappingToSave = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .build();

        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);

        UserRoleHierarchyLevelMapping result = repository.save(mappingToSave);

        assertNotNull(result);
        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    public void testFindById_Success() {
        when(jdbcTemplate.queryForObject(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(1)))
                .thenReturn(testMapping);

        Optional<UserRoleHierarchyLevelMapping> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getUserRoleId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(1));
    }

    @Test
    public void testFindById_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(999)))
                .thenThrow(new RuntimeException("No data found"));

        Optional<UserRoleHierarchyLevelMapping> result = repository.findById(999);

        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdate_Success() {
        UserRoleHierarchyLevelMapping updateMapping = UserRoleHierarchyLevelMapping.builder()
                .id(1)
                .userRoleId(101L)
                .userHierarchyLevelId(2)
                .build();

        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

        UserRoleHierarchyLevelMapping result = repository.update(updateMapping);

        assertNotNull(result);
        assertEquals(101L, result.getUserRoleId());
        assertEquals(2, result.getUserHierarchyLevelId());
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any());
    }

    @Test
    public void testDelete_Success() {
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(1);

        assertDoesNotThrow(() -> repository.delete(1));

        verify(jdbcTemplate, times(1)).update(anyString(), eq(1));
    }

    @Test
    public void testFindAll_Success() {
        UserRoleHierarchyLevelMapping mapping2 = UserRoleHierarchyLevelMapping.builder()
                .id(2)
                .userRoleId(101L)
                .userHierarchyLevelId(2)
                .build();

        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(testMapping, mapping2);

        when(jdbcTemplate.query(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class)))
                .thenReturn(mappings);

        List<UserRoleHierarchyLevelMapping> result = repository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class));
    }

    @Test
    public void testFindAll_Empty() {
        when(jdbcTemplate.query(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class)))
                .thenReturn(Arrays.asList());

        List<UserRoleHierarchyLevelMapping> result = repository.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByUserRoleId_Success() {
        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(testMapping);

        when(jdbcTemplate.query(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(100L)))
                .thenReturn(mappings);

        List<UserRoleHierarchyLevelMapping> result = repository.findByUserRoleId(100L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserRoleId());
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(100L));
    }

    @Test
    public void testFindByUserRoleId_Empty() {
        when(jdbcTemplate.query(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(999L)))
                .thenReturn(Arrays.asList());

        List<UserRoleHierarchyLevelMapping> result = repository.findByUserRoleId(999L);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByUserHierarchyLevelId_Success() {
        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(testMapping);

        when(jdbcTemplate.query(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(1)))
                .thenReturn(mappings);

        List<UserRoleHierarchyLevelMapping> result = repository.findByUserHierarchyLevelId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getUserHierarchyLevelId());
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(1));
    }

    @Test
    public void testFindByUserHierarchyLevelId_Empty() {
        when(jdbcTemplate.query(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(999)))
                .thenReturn(Arrays.asList());

        List<UserRoleHierarchyLevelMapping> result = repository.findByUserHierarchyLevelId(999);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByUserRoleIdAndHierarchyLevelId_Success() {
        when(jdbcTemplate.queryForObject(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(100L), eq(1)))
                .thenReturn(testMapping);

        Optional<UserRoleHierarchyLevelMapping> result = repository.findByUserRoleIdAndHierarchyLevelId(100L, 1);

        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getUserRoleId());
        assertEquals(1, result.get().getUserHierarchyLevelId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(100L), eq(1));
    }

    @Test
    public void testFindByUserRoleIdAndHierarchyLevelId_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(UserRoleHierarchyLevelMappingRowMapper.class), eq(999L), eq(999)))
                .thenThrow(new RuntimeException("No data found"));

        Optional<UserRoleHierarchyLevelMapping> result = repository.findByUserRoleIdAndHierarchyLevelId(999L, 999);

        assertFalse(result.isPresent());
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

package com.mahaexam.tenant.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;
import com.mahaexam.tenant.management.service.UserRoleHierarchyLevelMappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleHierarchyLevelMappingControllerTest {

    @Mock
    private UserRoleHierarchyLevelMappingService mappingService;

    @InjectMocks
    private UserRoleHierarchyLevelMappingController mappingController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserRoleHierarchyLevelMapping testMapping;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mappingController).build();
        objectMapper = new ObjectMapper();

        testMapping = UserRoleHierarchyLevelMapping.builder()
                .id(1)
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void testCreateMapping_Success() throws Exception {
        UserRoleHierarchyLevelMapping mappingToCreate = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .build();

        when(mappingService.save(any(UserRoleHierarchyLevelMapping.class))).thenReturn(testMapping);

        mockMvc.perform(post("/api/v1/user-role-hierarchy-mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mappingToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userRoleId").value(100))
                .andExpect(jsonPath("$.userHierarchyLevelId").value(1));

        verify(mappingService, times(1)).save(any(UserRoleHierarchyLevelMapping.class));
    }

    @Test
    public void testCreateMapping_BadRequest_NullUserRoleId() throws Exception {
        UserRoleHierarchyLevelMapping mappingToCreate = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(null)
                .userHierarchyLevelId(1)
                .build();

        when(mappingService.save(any(UserRoleHierarchyLevelMapping.class)))
                .thenThrow(new IllegalArgumentException("User Role ID is required and must be positive"));

        mockMvc.perform(post("/api/v1/user-role-hierarchy-mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mappingToCreate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateMapping_BadRequest_NullHierarchyLevelId() throws Exception {
        UserRoleHierarchyLevelMapping mappingToCreate = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(100L)
                .userHierarchyLevelId(null)
                .build();

        when(mappingService.save(any(UserRoleHierarchyLevelMapping.class)))
                .thenThrow(new IllegalArgumentException("User Hierarchy Level ID is required and must be positive"));

        mockMvc.perform(post("/api/v1/user-role-hierarchy-mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mappingToCreate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateMapping_Conflict() throws Exception {
        UserRoleHierarchyLevelMapping mappingToCreate = UserRoleHierarchyLevelMapping.builder()
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .build();

        when(mappingService.save(any(UserRoleHierarchyLevelMapping.class)))
                .thenThrow(new ValidationException("Mapping for User Role ID 100 and Hierarchy Level ID 1 already exists"));

        mockMvc.perform(post("/api/v1/user-role-hierarchy-mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mappingToCreate)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testGetMappingById_Success() throws Exception {
        when(mappingService.findById(1)).thenReturn(Optional.of(testMapping));

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userRoleId").value(100))
                .andExpect(jsonPath("$.userHierarchyLevelId").value(1));

        verify(mappingService, times(1)).findById(1);
    }

    @Test
    public void testGetMappingById_NotFound() throws Exception {
        when(mappingService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(mappingService, times(1)).findById(999);
    }

    @Test
    public void testUpdateMapping_Success() throws Exception {
        UserRoleHierarchyLevelMapping updateMapping = UserRoleHierarchyLevelMapping.builder()
                .id(1)
                .userRoleId(101L)
                .userHierarchyLevelId(2)
                .build();

        when(mappingService.update(any(UserRoleHierarchyLevelMapping.class))).thenReturn(updateMapping);

        mockMvc.perform(put("/api/v1/user-role-hierarchy-mappings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateMapping)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userRoleId").value(101))
                .andExpect(jsonPath("$.userHierarchyLevelId").value(2));

        verify(mappingService, times(1)).update(any(UserRoleHierarchyLevelMapping.class));
    }

    @Test
    public void testUpdateMapping_BadRequest() throws Exception {
        UserRoleHierarchyLevelMapping updateMapping = UserRoleHierarchyLevelMapping.builder()
                .id(1)
                .userRoleId(null)
                .userHierarchyLevelId(1)
                .build();

        when(mappingService.update(any(UserRoleHierarchyLevelMapping.class)))
                .thenThrow(new IllegalArgumentException("User Role ID is required and must be positive"));

        mockMvc.perform(put("/api/v1/user-role-hierarchy-mappings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateMapping)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateMapping_NotFound() throws Exception {
        UserRoleHierarchyLevelMapping updateMapping = UserRoleHierarchyLevelMapping.builder()
                .id(999)
                .userRoleId(100L)
                .userHierarchyLevelId(1)
                .build();

        when(mappingService.update(any(UserRoleHierarchyLevelMapping.class)))
                .thenThrow(new ValidationException("UserRoleHierarchyLevelMapping with ID 999 does not exist"));

        mockMvc.perform(put("/api/v1/user-role-hierarchy-mappings/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateMapping)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testGetAllMappings_Success() throws Exception {
        UserRoleHierarchyLevelMapping mapping2 = UserRoleHierarchyLevelMapping.builder()
                .id(2)
                .userRoleId(101L)
                .userHierarchyLevelId(2)
                .build();

        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(testMapping, mapping2);

        when(mappingService.findAll()).thenReturn(mappings);

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(mappingService, times(1)).findAll();
    }

    @Test
    public void testGetAllMappings_Empty() throws Exception {
        when(mappingService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(mappingService, times(1)).findAll();
    }

    @Test
    public void testGetMappingsByUserRoleId_Success() throws Exception {
        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(testMapping);

        when(mappingService.findByUserRoleId(100L)).thenReturn(mappings);

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings/by-role/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userRoleId").value(100));

        verify(mappingService, times(1)).findByUserRoleId(100L);
    }

    @Test
    public void testGetMappingsByUserRoleId_Empty() throws Exception {
        when(mappingService.findByUserRoleId(999L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings/by-role/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(mappingService, times(1)).findByUserRoleId(999L);
    }

    @Test
    public void testGetMappingsByHierarchyLevelId_Success() throws Exception {
        List<UserRoleHierarchyLevelMapping> mappings = Arrays.asList(testMapping);

        when(mappingService.findByUserHierarchyLevelId(1)).thenReturn(mappings);

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings/by-hierarchy-level/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userHierarchyLevelId").value(1));

        verify(mappingService, times(1)).findByUserHierarchyLevelId(1);
    }

    @Test
    public void testGetMappingsByHierarchyLevelId_Empty() throws Exception {
        when(mappingService.findByUserHierarchyLevelId(999)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings/by-hierarchy-level/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(mappingService, times(1)).findByUserHierarchyLevelId(999);
    }

    @Test
    public void testGetMappingByRoleAndLevel_Success() throws Exception {
        when(mappingService.findByUserRoleIdAndHierarchyLevelId(100L, 1)).thenReturn(Optional.of(testMapping));

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings/by-role/100/and-level/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userRoleId").value(100))
                .andExpect(jsonPath("$.userHierarchyLevelId").value(1));

        verify(mappingService, times(1)).findByUserRoleIdAndHierarchyLevelId(100L, 1);
    }

    @Test
    public void testGetMappingByRoleAndLevel_NotFound() throws Exception {
        when(mappingService.findByUserRoleIdAndHierarchyLevelId(999L, 999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/user-role-hierarchy-mappings/by-role/999/and-level/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(mappingService, times(1)).findByUserRoleIdAndHierarchyLevelId(999L, 999);
    }

    @Test
    public void testDeleteMapping_Success() throws Exception {
        doNothing().when(mappingService).delete(1);

        mockMvc.perform(delete("/api/v1/user-role-hierarchy-mappings/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(mappingService, times(1)).delete(1);
    }

    @Test
    public void testDeleteMapping_NotFound() throws Exception {
        doThrow(new ValidationException("UserRoleHierarchyLevelMapping with ID 999 does not exist"))
                .when(mappingService).delete(999);

        mockMvc.perform(delete("/api/v1/user-role-hierarchy-mappings/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(mappingService, times(1)).delete(999);
    }
}

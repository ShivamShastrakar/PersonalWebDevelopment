package com.mahaexam.common.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.SubjectGroup;
import com.mahaexam.common.repo.SubjectGroupRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectGroupServiceImplTest {

    @Mock
    private SubjectGroupRepository subjectGroupRepository;

    @InjectMocks
    private SubjectGroupServiceImpl subjectGroupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGroupsByTenant_shouldReturnGroups() {
        Long tenantId = 1L;
        List<SubjectGroup> groups = List.of(
                SubjectGroup.builder().groupId(1).groupName("Science").tenantId(tenantId).build(),
                SubjectGroup.builder().groupId(2).groupName("Commerce").tenantId(tenantId).build()
        );

        when(subjectGroupRepository.findAllByTenant(tenantId)).thenReturn(groups);

        List<SubjectGroup> result = subjectGroupService.getAllGroupsByTenant(tenantId);

        assertEquals(2, result.size());
        verify(subjectGroupRepository).findAllByTenant(tenantId);
    }

    @Test
    void getGroupById_shouldReturnGroup() {
        int id = 100;
        SubjectGroup group = SubjectGroup.builder()
                .groupId(id)
                .groupName("Arts")
                .tenantId(1L)
                .build();

        when(subjectGroupRepository.findById(id)).thenReturn(group);

        SubjectGroup result = subjectGroupService.getGroupById(id);

        assertEquals("Arts", result.getGroupName());
        verify(subjectGroupRepository).findById(id);
    }

    @Test
    void createGroup_shouldThrowExceptionWhenGroupNameExists() {
        SubjectGroup group = SubjectGroup.builder()
                .groupId(0)
                .groupName("Math")
                .tenantId(1L)
                .build();

        when(subjectGroupRepository.existsByGroupNameAndTenantId("Math", 1L)).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            subjectGroupService.createGroup(group);
        });

        assertEquals("Group name already exists for this tenant.", exception.getMessage());
        verify(subjectGroupRepository).existsByGroupNameAndTenantId("Math", 1L);
    }

    @Test
    void createGroup_shouldSaveSuccessfully() {
        SubjectGroup group = SubjectGroup.builder()
                .groupId(0)
                .groupName("Physics")
                .tenantId(1L)
                .build();

        when(subjectGroupRepository.existsByGroupNameAndTenantId("Physics", 1L)).thenReturn(false);
        when(subjectGroupRepository.save(group)).thenReturn(1);

        int result = subjectGroupService.createGroup(group);

        assertEquals(1, result);
        verify(subjectGroupRepository).save(group);
    }

    @Test
    void updateGroup_shouldThrowExceptionWhenGroupNameAlreadyExists() {
        SubjectGroup group = SubjectGroup.builder()
                .groupId(10)
                .groupName("Bio")
                .tenantId(1L)
                .build();

        when(subjectGroupRepository.existsByGroupNameAndTenantIdExceptId("Bio", 1L, 10)).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            subjectGroupService.updateGroup(group);
        });

        assertEquals("Group name already exists for this tenant.", exception.getMessage());
        verify(subjectGroupRepository).existsByGroupNameAndTenantIdExceptId("Bio", 1L, 10);
    }

    @Test
    void updateGroup_shouldUpdateSuccessfully() {
        SubjectGroup group = SubjectGroup.builder()
                .groupId(20)
                .groupName("English")
                .tenantId(2L)
                .build();

        when(subjectGroupRepository.existsByGroupNameAndTenantIdExceptId("English", 2L, 20)).thenReturn(false);
        when(subjectGroupRepository.update(group)).thenReturn(1);

        int result = subjectGroupService.updateGroup(group);

        assertEquals(1, result);
        verify(subjectGroupRepository).update(group);
    }

    @Test
    void deleteGroup_shouldCallSoftDelete() {
        int id = 45;
        when(subjectGroupRepository.softDelete(id)).thenReturn(1);

        int result = subjectGroupService.deleteGroup(id);

        assertEquals(1, result);
        verify(subjectGroupRepository).softDelete(id);
    }
}
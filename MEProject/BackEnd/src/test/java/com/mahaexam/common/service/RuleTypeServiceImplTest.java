package com.mahaexam.common.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.mahaexam.common.bean.RuleTypeBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.RuleTypeModel;
import com.mahaexam.common.repo.RuleTypeRepository;


class RuleTypeServiceImplTest {

    @Mock
    private RuleTypeRepository ruleTypeRepository;

    @InjectMocks
    private RuleTypeServiceImpl ruleTypeService;

    private RuleTypeBean validBean;
    private RuleTypeModel validModel;
    private UserBean testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validBean = RuleTypeBean.builder()
                .id(1)
                .ruleType("Automatic")
                .createdBy(1)
                .updatedBy(2)
                .tenantId(123L)
                .build();

        validModel = RuleTypeModel.builder()
                .id(1)
                .ruleType("Automatic")
                .createdBy(1)
                .updatedBy(2)
                .tenantId(123L)
                .createdAt(LocalDateTime.now())
                .deleted("0")
                .build();

        testUser = UserBean.builder()
                .tenantId(123L)
                .build();
    }

    @Test
    void testCreateRuleTypeSuccess() {
        when(ruleTypeRepository.save(any(RuleTypeModel.class))).thenReturn(validModel);

        RuleTypeBean result = ruleTypeService.createRuleType(validBean);

        assertNotNull(result);
        assertEquals(validBean.getRuleType(), result.getRuleType());
        verify(ruleTypeRepository, times(1)).save(any(RuleTypeModel.class));
    }

    @Test
    void testCreateRuleTypeInvalid() {
        RuleTypeBean invalidBean = RuleTypeBean.builder().build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ruleTypeService.createRuleType(invalidBean));
        assertEquals("Rule type cannot be null or empty", exception.getMessage());
    }

    @Test
    void testGetRuleTypeByIdSuccess() {
        when(ruleTypeRepository.findById(1)).thenReturn(Optional.of(validModel));

        Optional<RuleTypeBean> result = ruleTypeService.getRuleTypeById(1);

        assertTrue(result.isPresent());
        assertEquals(validBean.getRuleType(), result.get().getRuleType());
    }

    @Test
    void testGetRuleTypeByIdInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ruleTypeService.getRuleTypeById(-1));
        assertEquals("Invalid rule type ID", exception.getMessage());
    }

    @Test
    void testGetAllRuleTypes() {
        when(ruleTypeRepository.findAll(testUser)).thenReturn(List.of(validModel));

        List<RuleTypeBean> result = ruleTypeService.getAllRuleTypes(testUser);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(validBean.getRuleType(), result.get(0).getRuleType());
    }

    @Test
    void testGetAllRuleTypesInvalidUser() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ruleTypeService.getAllRuleTypes(new UserBean()));
        assertEquals("User or tenant ID cannot be null", exception.getMessage());
    }

    @Test
    void testUpdateRuleTypeSuccess() {
        when(ruleTypeRepository.findById(1)).thenReturn(Optional.of(validModel));

        RuleTypeBean updatedBean = RuleTypeBean.builder()
                .id(1)
                .ruleType("Manual")
                .createdBy(1)
                .updatedBy(2)
                .tenantId(123L)
                .build();

        RuleTypeBean result = ruleTypeService.updateRuleType(1, updatedBean);

        assertNotNull(result);
        assertEquals("Manual", result.getRuleType());
        verify(ruleTypeRepository).update(any(RuleTypeModel.class));
    }

    @Test
    void testUpdateRuleTypeNotFound() {
        when(ruleTypeRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ruleTypeService.updateRuleType(1, validBean));
        assertEquals("Rule type with ID 1 not found", exception.getMessage());
    }

    @Test
    void testDeleteRuleTypeSuccess() {
        when(ruleTypeRepository.findById(1)).thenReturn(Optional.of(validModel));

        assertDoesNotThrow(() -> ruleTypeService.deleteRuleType(1));
        verify(ruleTypeRepository).delete(1);
    }

    @Test
    void testDeleteRuleTypeNotFound() {
        when(ruleTypeRepository.findById(99)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ruleTypeService.deleteRuleType(99));
        assertEquals("Rule type with ID 99 not found", exception.getMessage());
    }
}
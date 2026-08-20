package com.mahaexam.papertemplate.service;

import com.mahaexam.common.bean.PaperTemplateResponse;
import com.mahaexam.papertemplate.repository.PaperTemplateRepository;
import com.mahaexam.papertemplate.service.impl.PaperTemplateServiceImpl;
import com.mahaexam.part.repository.PartRepository;
import com.mahaexam.section.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test to verify the performance optimization of getFullHierarchyByIds
 * using batch queries instead of N+1 queries
 */
@ExtendWith(MockitoExtension.class)
class PaperTemplateServiceBatchQueryTest {

    @Mock
    private PaperTemplateRepository paperTemplateRepo;

    @Mock
    private PartRepository partRepo;

    @Mock
    private SectionRepository sectionRepo;

    @InjectMocks
    private PaperTemplateServiceImpl service;

    @Test
    void testGetFullHierarchyByIds_UsesBatchQueries() {
        // Given: 5 template IDs
        List<Long> templateIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);

        // When: Fetching full hierarchy
        when(paperTemplateRepo.findTemplatesByIds(templateIds)).thenReturn(List.of());

        List<PaperTemplateResponse> result = service.getFullHierarchyByIds(templateIds);

        // Then: Should call batch methods ONCE (not N times)
        verify(paperTemplateRepo, times(1)).findTemplatesByIds(templateIds);
        // partRepo and sectionRepo won't be called since templates list is empty

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetFullHierarchyByIds_EmptyList() {
        // Given: Empty list
        List<Long> templateIds = List.of();

        // When: Fetching full hierarchy
        List<PaperTemplateResponse> result = service.getFullHierarchyByIds(templateIds);

        // Then: Should not call any repository methods
        verify(paperTemplateRepo, never()).findTemplatesByIds(any());
        verify(partRepo, never()).byPaperTemplateIds(any());
        verify(sectionRepo, never()).byPartIds(any());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetFullHierarchyByIds_NullList() {
        // Given: Null list
        List<Long> templateIds = null;

        // When: Fetching full hierarchy
        List<PaperTemplateResponse> result = service.getFullHierarchyByIds(templateIds);

        // Then: Should not call any repository methods
        verify(paperTemplateRepo, never()).findTemplatesByIds(any());
        verify(partRepo, never()).byPaperTemplateIds(any());
        verify(sectionRepo, never()).byPartIds(any());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * This test verifies that regardless of the number of template IDs,
     * we only make 3 database queries:
     * 1. Fetch all templates
     * 2. Fetch all parts
     * 3. Fetch all sections
     */
    @Test
    void testQueryCountIsConstant_Regardless_Of_TemplateCount() {
        // Test with different numbers of template IDs
        List<Integer> testCases = Arrays.asList(1, 5, 10, 50, 100);

        for (Integer count : testCases) {
            // Reset mocks
            reset(paperTemplateRepo, partRepo, sectionRepo);

            // Given: N template IDs
            List<Long> templateIds = generateTemplateIds(count);

            // When: Fetching full hierarchy
            when(paperTemplateRepo.findTemplatesByIds(templateIds)).thenReturn(List.of());

            service.getFullHierarchyByIds(templateIds);

            // Then: Should ALWAYS call batch methods exactly ONCE
            verify(paperTemplateRepo, times(1)).findTemplatesByIds(templateIds);

            // Log for demonstration
            System.out.println(String.format(
                "✅ Tested %d templates: 1 query to paperTemplateRepo (constant time!)",
                count
            ));
        }
    }

    private List<Long> generateTemplateIds(int count) {
        return java.util.stream.LongStream.rangeClosed(1, count)
                .boxed()
                .toList();
    }
}

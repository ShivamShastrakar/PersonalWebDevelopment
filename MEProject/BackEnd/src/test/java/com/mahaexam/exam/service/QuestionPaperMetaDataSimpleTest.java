package com.mahaexam.exam.service;

import com.mahaexam.common.bean.QuestionPaperMetaData;
import com.mahaexam.common.bean.QuestionPaperRequestDTO;
import com.mahaexam.common.bean.QuestionPaperResponseDTO;
import com.mahaexam.common.bean.QuestionPaperMapper;
import com.mahaexam.papertemplate.model.QuestionPaper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple unit test to verify Question Paper metadata structure (no Spring context needed)
 * Note: Using existing 'description' field for additional notes
 */
class QuestionPaperMetaDataSimpleTest {

    @Test
    void testMetaDataStructure() {
        // Create SUKA Distribution
        QuestionPaperMetaData.SkillDistribution skillDist = QuestionPaperMetaData.SkillDistribution.builder()
                .skill(25.0)
                .understanding(30.0)
                .knowledge(25.0)
                .application(20.0)
                .build();

        // Verify SUKA Distribution
        assertEquals(25.0, skillDist.getSkill());
        assertEquals(30.0, skillDist.getUnderstanding());
        assertEquals(25.0, skillDist.getKnowledge());
        assertEquals(20.0, skillDist.getApplication());

        // Create Difficulty Distribution
        QuestionPaperMetaData.DifficultyDistribution diffDist = QuestionPaperMetaData.DifficultyDistribution.builder()
                .hard(20.0)
                .medium(50.0)
                .easy(30.0)
                .build();

        // Verify Difficulty Distribution
        assertEquals(20.0, diffDist.getHard());
        assertEquals(50.0, diffDist.getMedium());
        assertEquals(30.0, diffDist.getEasy());

        // Create Complete Metadata
        QuestionPaperMetaData metaData = QuestionPaperMetaData.builder()
                .skillDistribution(skillDist)
                .difficultyDistribution(diffDist)
                .build();

        // Verify Metadata Structure
        assertNotNull(metaData.getSkillDistribution());
        assertNotNull(metaData.getDifficultyDistribution());
        assertEquals(25.0, metaData.getSkillDistribution().getSkill());
        assertEquals(20.0, metaData.getDifficultyDistribution().getHard());

        System.out.println("✅ Metadata structure test passed!");
    }

    @Test
    void testRequestDTOWithMetaDataAndDescription() {
        // Create metadata
        QuestionPaperMetaData metaData = QuestionPaperMetaData.builder()
                .skillDistribution(QuestionPaperMetaData.SkillDistribution.builder()
                        .skill(25.0)
                        .understanding(30.0)
                        .knowledge(25.0)
                        .application(20.0)
                        .build())
                .difficultyDistribution(QuestionPaperMetaData.DifficultyDistribution.builder()
                        .hard(20.0)
                        .medium(50.0)
                        .easy(30.0)
                        .build())
                .build();

        // Create request with metadata AND description (existing field)
        QuestionPaperRequestDTO request = new QuestionPaperRequestDTO();
        request.setQuestionPaperName("Mathematics Final Exam 2026");
        request.setAcademicYear("2025-2026");
        request.setStatus("DRAFT");
        request.setMetaData(metaData);
        request.setDescription("Balanced distribution focusing on understanding and medium difficulty");

        // Verify request structure
        assertEquals("Mathematics Final Exam 2026", request.getQuestionPaperName());
        assertNotNull(request.getMetaData());
        assertNotNull(request.getDescription());
        assertEquals("Balanced distribution focusing on understanding and medium difficulty", request.getDescription());

        // Verify metadata is separate from description
        assertNotNull(request.getMetaData().getSkillDistribution());
        assertNotNull(request.getMetaData().getDifficultyDistribution());

        System.out.println("✅ Request DTO structure test passed!");
        System.out.println("   - metaData: " + request.getMetaData());
        System.out.println("   - description: " + request.getDescription());
    }

    @Test
    void testResponseDTOWithMetaDataAndDescription() {
        // Create response
        QuestionPaperResponseDTO response = new QuestionPaperResponseDTO();
        response.setId(1L);
        response.setQuestionPaperName("Math Exam");

        // Set metadata
        QuestionPaperMetaData metaData = QuestionPaperMetaData.builder()
                .skillDistribution(QuestionPaperMetaData.SkillDistribution.builder()
                        .skill(25.0).understanding(30.0).knowledge(25.0).application(20.0)
                        .build())
                .difficultyDistribution(QuestionPaperMetaData.DifficultyDistribution.builder()
                        .hard(20.0).medium(50.0).easy(30.0)
                        .build())
                .build();
        response.setMetaData(metaData);

        // Set description (existing field)
        response.setDescription("Exam focuses on conceptual understanding");

        // Verify structure
        assertNotNull(response.getMetaData());
        assertNotNull(response.getDescription());
        assertEquals("Exam focuses on conceptual understanding", response.getDescription());
        assertEquals(25.0, response.getMetaData().getSkillDistribution().getSkill());

        System.out.println("✅ Response DTO structure test passed!");
    }

    @Test
    void testMapperWithMetaDataAndDescription() {
        // Create request
        QuestionPaperMetaData metaData = QuestionPaperMetaData.builder()
                .skillDistribution(QuestionPaperMetaData.SkillDistribution.builder()
                        .skill(25.0).understanding(30.0).knowledge(25.0).application(20.0)
                        .build())
                .difficultyDistribution(QuestionPaperMetaData.DifficultyDistribution.builder()
                        .hard(20.0).medium(50.0).easy(30.0)
                        .build())
                .build();

        QuestionPaperRequestDTO request = new QuestionPaperRequestDTO();
        request.setQuestionPaperName("Test Paper");
        request.setAcademicYear("2025-2026");
        request.setStatus("DRAFT");
        request.setMetaData(metaData);
        request.setDescription("Test description for the question paper");

        // Map to entity
        QuestionPaper entity = QuestionPaperMapper.toEntity(request);

        // Verify entity has both metaData and description
        assertNotNull(entity);
        assertEquals("Test Paper", entity.getQuestionPaperName());
        assertNotNull(entity.getMetaData());
        assertNotNull(entity.getDescription());
        assertEquals("Test description for the question paper", entity.getDescription());

        // Map back to response
        entity.setId(1L);
        entity.setCreatedAt(LocalDateTime.now());
        QuestionPaperResponseDTO response = QuestionPaperMapper.toResponse(entity);

        // Verify response has both metaData and description
        assertNotNull(response.getMetaData());
        assertNotNull(response.getDescription());
        assertEquals("Test description for the question paper", response.getDescription());
        assertEquals(25.0, response.getMetaData().getSkillDistribution().getSkill());

        System.out.println("✅ Mapper test passed!");
        System.out.println("   - Entity description: " + entity.getDescription());
        System.out.println("   - Response description: " + response.getDescription());
    }

    @Test
    void testDescriptionFieldUsedForNotes() {
        // This test verifies that we use the existing 'description' field for notes
        QuestionPaperRequestDTO request = new QuestionPaperRequestDTO();

        // Set metadata
        request.setMetaData(QuestionPaperMetaData.builder()
                .skillDistribution(QuestionPaperMetaData.SkillDistribution.builder()
                        .skill(25.0).understanding(30.0).knowledge(25.0).application(20.0)
                        .build())
                .difficultyDistribution(QuestionPaperMetaData.DifficultyDistribution.builder()
                        .hard(20.0).medium(50.0).easy(30.0)
                        .build())
                .build());

        // Set description (used for additional notes)
        request.setDescription("This description field is used for additional notes about the distribution");

        // Verify: description should be accessible directly from request
        assertNotNull(request.getDescription(), "Description should be accessible from request");
        assertEquals("This description field is used for additional notes about the distribution", request.getDescription());

        // Verify: metaData does NOT contain description/notes
        // metaData only has skillDistribution and difficultyDistribution
        assertNotNull(request.getMetaData());
        assertNotNull(request.getMetaData().getSkillDistribution());
        assertNotNull(request.getMetaData().getDifficultyDistribution());

        System.out.println("✅ Structure verification passed!");
        System.out.println("   ✓ description field is used for additional notes");
        System.out.println("   ✓ metaData contains only skill and difficulty distributions");
    }
}

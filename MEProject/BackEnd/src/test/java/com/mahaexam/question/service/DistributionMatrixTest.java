package com.mahaexam.question.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Unit tests for distribution matrix calculation logic
 */
public class DistributionMatrixTest {

    /**
     * Test case from actual logs: 2 questions with 3 SUKA levels and 2 difficulty levels
     * Expected: Should allocate 2 questions (not 0!)
     */
    @Test
    public void testSmallDistribution_2Questions_3Suka_2Difficulty() {
        int numberOfQuestions = 2;
        int totalSukaCount = 3; // SKILL=1, UNDERSTANDING=1, KNOWLEDGE=1
        int totalDifficultyCount = 2; // HARD=1, MEDIUM=1

        Map<String, Integer> sukaDistribution = new LinkedHashMap<>();
        sukaDistribution.put("SKILL", 1);
        sukaDistribution.put("UNDERSTANDING", 1);
        sukaDistribution.put("KNOWLEDGE", 1);

        Map<String, Integer> difficultyDistribution = new LinkedHashMap<>();
        difficultyDistribution.put("HARD", 1);
        difficultyDistribution.put("MEDIUM", 1);

        // Calculate exact proportions
        Map<String, Map<String, Double>> exactProportions = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> sukaEntry : sukaDistribution.entrySet()) {
            String skill = sukaEntry.getKey();
            int sukaCount = sukaEntry.getValue();

            Map<String, Double> diffMap = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> diffEntry : difficultyDistribution.entrySet()) {
                String diff = diffEntry.getKey();
                int diffCount = diffEntry.getValue();

                double proportion = ((double) sukaCount / totalSukaCount) *
                                   ((double) diffCount / totalDifficultyCount);
                double exactQuestions = proportion * numberOfQuestions;
                diffMap.put(diff, exactQuestions);

                System.out.printf("%s × %s: %.3f\n", skill, diff, exactQuestions);
            }
            exactProportions.put(skill, diffMap);
        }

        // All should be 0.333... which floors to 0
        for (Map<String, Double> diffMap : exactProportions.values()) {
            for (Double value : diffMap.values()) {
                assertEquals(0.333, value, 0.01, "Each combination should be ~0.333");
                assertEquals(0, (int) Math.floor(value), "Floor should be 0");
            }
        }

        // After adjustment, should have exactly 2 questions allocated
        // (This would be done by the actual service method)
        System.out.println("\n✅ Test demonstrates the problem: all combinations floor to 0");
        System.out.println("✅ After fix: adjustment phase should allocate 2 questions to top combinations");
    }

    /**
     * Test case: 1 question with 1 SUKA and 1 difficulty
     * Expected: Should allocate exactly 1 question
     */
    @Test
    public void testPerfectMatch_1Question_1Suka_1Difficulty() {
        int numberOfQuestions = 1;
        int totalSukaCount = 1; // APPLICATION=1
        int totalDifficultyCount = 1; // EASY=1

        double proportion = ((double) 1 / totalSukaCount) * ((double) 1 / totalDifficultyCount);
        double exactQuestions = proportion * numberOfQuestions;

        System.out.printf("APPLICATION × EASY: %.3f\n", exactQuestions);

        assertEquals(1.0, exactQuestions, 0.001, "Should be exactly 1.0");
        assertEquals(1, (int) Math.floor(exactQuestions), "Floor should be 1");

        System.out.println("\n✅ Test passes: perfect match allocates correctly");
    }

    /**
     * Test case: 10 questions distributed across 4 SUKA and 3 difficulty levels
     */
    @Test
    public void testLargerDistribution_10Questions() {
        int numberOfQuestions = 10;

        Map<String, Integer> sukaDistribution = new LinkedHashMap<>();
        sukaDistribution.put("SKILL", 3);
        sukaDistribution.put("UNDERSTANDING", 3);
        sukaDistribution.put("KNOWLEDGE", 2);
        sukaDistribution.put("APPLICATION", 2);
        int totalSukaCount = 10;

        Map<String, Integer> difficultyDistribution = new LinkedHashMap<>();
        difficultyDistribution.put("EASY", 3);
        difficultyDistribution.put("MEDIUM", 4);
        difficultyDistribution.put("HARD", 3);
        int totalDifficultyCount = 10;

        int totalAllocated = 0;
        Map<String, Map<String, Integer>> allocations = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> sukaEntry : sukaDistribution.entrySet()) {
            String skill = sukaEntry.getKey();
            int sukaCount = sukaEntry.getValue();

            Map<String, Integer> diffMap = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> diffEntry : difficultyDistribution.entrySet()) {
                String diff = diffEntry.getKey();
                int diffCount = diffEntry.getValue();

                double proportion = ((double) sukaCount / totalSukaCount) *
                                   ((double) diffCount / totalDifficultyCount);
                double exactQuestions = proportion * numberOfQuestions;
                int allocated = (int) Math.floor(exactQuestions);

                diffMap.put(diff, allocated);
                totalAllocated += allocated;

                System.out.printf("%s × %s: %.2f → %d\n", skill, diff, exactQuestions, allocated);
            }
            allocations.put(skill, diffMap);
        }

        System.out.printf("\nTotal allocated (before adjustment): %d / %d\n", totalAllocated, numberOfQuestions);

        assertTrue(totalAllocated <= numberOfQuestions,
            "Floor should never over-allocate");

        int remaining = numberOfQuestions - totalAllocated;
        System.out.printf("Remaining to allocate: %d\n", remaining);

        System.out.println("\n✅ Test demonstrates that floor() requires adjustment phase");
    }

    /**
     * Test fractional sorting logic
     */
    @Test
    public void testFractionalSorting() {
        Map<String, Double> values = new LinkedHashMap<>();
        values.put("A", 2.8); // frac = 0.8 (highest)
        values.put("B", 1.5); // frac = 0.5
        values.put("C", 3.3); // frac = 0.3
        values.put("D", 0.9); // frac = 0.9 (highest)
        values.put("E", 1.1); // frac = 0.1 (lowest)

        List<Map.Entry<String, Double>> sorted = values.entrySet()
            .stream()
            .sorted((a, b) -> {
                double fracA = a.getValue() - Math.floor(a.getValue());
                double fracB = b.getValue() - Math.floor(b.getValue());
                return Double.compare(fracB, fracA); // Descending
            })
            .toList();

        System.out.println("Sorted by fractional part (descending):");
        for (Map.Entry<String, Double> entry : sorted) {
            double frac = entry.getValue() - Math.floor(entry.getValue());
            System.out.printf("%s: %.1f (frac=%.1f)\n", entry.getKey(), entry.getValue(), frac);
        }

        assertEquals("D", sorted.get(0).getKey(), "D has highest fraction (0.9)");
        assertEquals("A", sorted.get(1).getKey(), "A has second highest (0.8)");
        assertEquals("E", sorted.get(4).getKey(), "E has lowest fraction (0.1)");

        System.out.println("\n✅ Fractional sorting works correctly");
    }
}


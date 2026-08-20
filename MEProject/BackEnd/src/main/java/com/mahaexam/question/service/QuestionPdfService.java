package com.mahaexam.question.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.mahaexam.question.model.QuestionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Service for generating PDF documents from questions
 * Supports English, Hindi, and Marathi languages
 */
@Service
public class QuestionPdfService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionPdfService.class);
    private final ObjectMapper objectMapper;
    // Cache font bytes to avoid repeated loading, but create new PdfFont for each document
    private byte[] devanagariFontBytes;
    private boolean useBuiltInDevanagariFont = false;

    public QuestionPdfService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        loadFontResources();
    }

    /**
     * Load font resources once (bytes) but create new PdfFont instances for each document
     * This avoids the "PDF indirect object belongs to other PDF document" error
     */
    private void loadFontResources() {
        try {
            // Try to load custom Noto Sans Devanagari from resources first
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/NotoSansDevanagari-Regular.ttf");
            if (fontStream != null) {
                try {
                    devanagariFontBytes = fontStream.readAllBytes();
                    logger.info("✓ Loaded custom Noto Sans Devanagari font bytes from resources");
                    fontStream.close();
                    useBuiltInDevanagariFont = false;
                } catch (Exception e) {
                    logger.debug("Could not load custom font from resources, will use built-in font", e);
                    fontStream.close();
                    useBuiltInDevanagariFont = true;
                }
            } else {
                // Use built-in font with Devanagari support from font-asian library
                logger.info("Custom font not found, will use built-in Devanagari font from iText font-asian library");
                useBuiltInDevanagariFont = true;
            }
        } catch (Exception e) {
            logger.warn("Error loading Devanagari font resources, will use default font. Hindi/Marathi rendering may be limited", e);
            useBuiltInDevanagariFont = true;
        }
    }

    /**
     * Create fresh font instances for a new PDF document
     * Must be called for each new PDF to avoid object sharing issues
     */
    private PdfFont createDevanagariFont() throws Exception {
        if (devanagariFontBytes != null) {
            return PdfFontFactory.createFont(devanagariFontBytes, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        } else if (useBuiltInDevanagariFont) {
            return createFallbackDevanagariFont();
        } else {
            return PdfFontFactory.createFont();
        }
    }

    /**
     * Create a fresh regular font instance for a new PDF document
     */
    private PdfFont createRegularFont() throws Exception {
        return PdfFontFactory.createFont();
    }

    /**
     * Create a fallback font that supports Devanagari script
     * Uses FreeSans from iText's font-asian library which has good Unicode support
     */
    private PdfFont createFallbackDevanagariFont() throws Exception {
        try {
            // FreeSans from font-asian library supports Devanagari
            // PdfFontFactory.EMBEDDED means the font will be embedded in PDF
            PdfFont font = PdfFontFactory.createFont("FreeSans.ttf", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
            logger.info("✓ Using FreeSans font with Devanagari support from font-asian library");
            return font;
        } catch (Exception e) {
            // Final fallback - use default font
            logger.warn("FreeSans not available, using default font. Devanagari characters may not render correctly");
            return PdfFontFactory.createFont();
        }
    }

    /**
     * Detect if text contains Devanagari characters (Hindi/Marathi)
     */
    private boolean containsDevanagari(String text) {
        if (text == null) return false;
        return text.codePoints().anyMatch(cp -> (cp >= 0x0900 && cp <= 0x097F));
    }

    /**
     * Get appropriate font for text based on language
     */
    private PdfFont getFontForText(String text, PdfFont devanagariFont, PdfFont regularFont) {
        return containsDevanagari(text) ? devanagariFont : regularFont;
    }

    /**
     * Generate PDF with questions and answers
     * Supports English, Hindi, and Marathi
     */
    public byte[] generateQuestionPdf(List<QuestionEntity> questions, com.mahaexam.openai.model.QuestionMetadata metadata) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Create fresh font instances for this PDF document
            PdfFont devanagariFont = createDevanagariFont();
            PdfFont regularFont = createRegularFont();

            // Detect language from actual question content
            String detectedLanguage = detectLanguageFromQuestions(questions);

            // Add title in appropriate language with professional styling
            String titleText = getTitleInLanguage(detectedLanguage);
            Paragraph title = new Paragraph(titleText)
                    .setFont(getFontForText(titleText, devanagariFont, regularFont))
                    .setFontSize(24)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(0, 51, 102)) // Dark blue
                    .setBackgroundColor(new DeviceRgb(230, 240, 255)) // Light blue background
                    .setPadding(15)
                    .setMarginBottom(25);
            document.add(title);

            // Add information section if metadata is provided
            if (metadata != null) {
                addInformationSection(document, questions, metadata, detectedLanguage, devanagariFont, regularFont);
            }

            // Add questions
            // Process questions to keep paragraphText only with the first question of each paragraph group
            java.util.Set<String> processedParagraphIds = new java.util.HashSet<>();
            for (int i = 0; i < questions.size(); i++) {
                QuestionEntity question = questions.get(i);

                // If this question has a paragraphId, check if we've already processed this paragraph group
                if (question.getParagraphId() != null && !question.getParagraphId().isEmpty()) {
                    if (processedParagraphIds.contains(question.getParagraphId())) {
                        // Not the first question in this paragraph group - clear the paragraphText
                        // Create a copy to avoid modifying the original object
                        QuestionEntity modifiedQuestion = QuestionEntity.builder()
                                .id(question.getId())
                                .boardId(question.getBoardId())
                                .subjectId(question.getSubjectId())
                                .classId(question.getClassId())
                                .medium(question.getMedium())
                                .chapterId(question.getChapterId())
                                .topicId(question.getTopicId())
                                .questionType(question.getQuestionType())
                                .questionText(question.getQuestionText())
                                .options(question.getOptions())
                                .correctAnswer(question.getCorrectAnswer())
                                .answerExplanation(question.getAnswerExplanation())
                                .skillLevel(question.getSkillLevel())
                                .difficultyLevel(question.getDifficultyLevel())
                                .aiPromptHash(question.getAiPromptHash())
                                .createdAt(question.getCreatedAt())
                                .createdBy(question.getCreatedBy())
                                .paragraphId(question.getParagraphId())
                                .paragraphText(null) // Clear paragraph text for subsequent questions
                                .build();
                        addQuestionToDocument(document, modifiedQuestion, i + 1, devanagariFont, regularFont);
                    } else {
                        // First question in this paragraph group - keep the paragraphText
                        processedParagraphIds.add(question.getParagraphId());
                        addQuestionToDocument(document, question, i + 1, devanagariFont, regularFont);
                    }
                } else {
                    // Regular question without paragraphId - add as is
                    addQuestionToDocument(document, question, i + 1, devanagariFont, regularFont);
                }
            }

            // Add answer key section with professional styling
            document.add(new Paragraph("\n\n"));
            String answerTitleText = getAnswerTitleInLanguage(detectedLanguage);
            Paragraph answerTitle = new Paragraph(answerTitleText)
                    .setFont(getFontForText(answerTitleText, devanagariFont, regularFont))
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(102, 0, 102)) // Purple
                    .setBackgroundColor(new DeviceRgb(250, 240, 255)) // Light purple background
                    .setPadding(12)
                    .setMarginBottom(20)
                    .setMarginTop(10);
            document.add(answerTitle);

            // Add answers
            for (int i = 0; i < questions.size(); i++) {
                QuestionEntity question = questions.get(i);
                addAnswerToDocument(document, question, i + 1, devanagariFont, regularFont);
            }

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            logger.error("Error generating PDF", e);
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    /**
     * Add a question to the document
     */
    private void addQuestionToDocument(Document document, QuestionEntity question, int questionNumber,
                                      PdfFont devanagariFont, PdfFont regularFont) {
        try {
            // Add paragraph text if present (for paragraph-type questions)
            if (question.getParagraphText() != null && !question.getParagraphText().isEmpty()) {
                String paragraphText = question.getParagraphText();
                PdfFont paragraphFont = getFontForText(paragraphText, devanagariFont, regularFont);

                Paragraph paragraphPara = new Paragraph(paragraphText)
                        .setFont(paragraphFont)
                        .setFontSize(11)
                        .setFontColor(new DeviceRgb(51, 51, 51)) // Dark gray text
                        .setBackgroundColor(new DeviceRgb(245, 245, 250)) // Light gray-blue background
                        .setPadding(12)
                        .setPaddingLeft(15)
                        .setPaddingRight(15)
                        .setMarginBottom(10)
                        .setMarginTop(5)
                        .setItalic();
                document.add(paragraphPara);
            }

            // Question number and text with appropriate font and professional styling
            String questionText = question.getQuestionText();
            PdfFont font = getFontForText(questionText, devanagariFont, regularFont);

            Paragraph questionPara = new Paragraph()
                    .add(new Paragraph("Q" + questionNumber + ". ").setFont(font).setBold()
                            .setFontColor(new DeviceRgb(204, 0, 0))) // Red for question number
                    .add(new Paragraph(questionText).setFont(font)
                            .setFontColor(new DeviceRgb(0, 0, 0))) // Black for question text
                    .setBackgroundColor(new DeviceRgb(255, 250, 240)) // Light cream background
                    .setPadding(10)
                    .setMarginBottom(8)
                    .setMarginTop(5);
            document.add(questionPara);

            // Parse and add options if available
            if (question.getOptions() != null) {
                JsonNode optionsNode = objectMapper.readTree(question.getOptions());

                Table optionsTable = new Table(UnitValue.createPercentArray(1))
                        .useAllAvailableWidth()
                        .setMarginLeft(20)
                        .setMarginBottom(10);

                for (int i = 1; i <= 4; i++) {
                    String optionKey = "option" + i;
                    if (optionsNode.has(optionKey)) {
                        String optionText = optionsNode.get(optionKey).asText();
                        char optionLetter = (char)('A' + i - 1);
                        PdfFont optionFont = getFontForText(optionText, devanagariFont, regularFont);

                        // Alternating background colors for options
                        DeviceRgb bgColor = (i % 2 == 0) ?
                                new DeviceRgb(248, 248, 255) : // Very light blue
                                new DeviceRgb(255, 255, 255);  // White

                        Paragraph optionPara = new Paragraph()
                                .add(new Paragraph(optionLetter + ". ").setFont(optionFont).setBold()
                                        .setFontColor(new DeviceRgb(0, 102, 204))) // Blue for option letter
                                .add(new Paragraph(optionText).setFont(optionFont)
                                        .setFontColor(new DeviceRgb(51, 51, 51))); // Dark gray for text

                        Cell cell = new Cell()
                                .add(optionPara)
                                .setBackgroundColor(bgColor)
                                .setBorder(null)
                                .setPadding(6)
                                .setPaddingLeft(15);
                        optionsTable.addCell(cell);
                    }
                }

                document.add(optionsTable);
            }

            document.add(new Paragraph("\n"));
        } catch (Exception e) {
            logger.error("Error adding question to document", e);
        }
    }

    /**
     * Add answer explanation to the document
     */
    private void addAnswerToDocument(Document document, QuestionEntity question, int questionNumber,
                                    PdfFont devanagariFont, PdfFont regularFont) {
        try {
            // Detect language from question text content (not medium field)
            String detectedLanguage = detectLanguageFromText(question.getQuestionText());
            String answerLabel = getAnswerLabelInLanguage(detectedLanguage, questionNumber);
            PdfFont font = getFontForText(answerLabel, devanagariFont, regularFont);

            Paragraph answerHeader = new Paragraph(answerLabel)
                    .setFont(font)
                    .setBold()
                    .setFontSize(13)
                    .setFontColor(new DeviceRgb(153, 0, 76)) // Dark pink/maroon
                    .setBackgroundColor(new DeviceRgb(255, 245, 250)) // Very light pink
                    .setPadding(6)
                    .setMarginTop(12)
                    .setMarginBottom(5);
            document.add(answerHeader);

            // Correct answer with green highlight
            if (question.getCorrectAnswer() != null) {
                JsonNode correctAnswerNode = objectMapper.readTree(question.getCorrectAnswer());
                int correctOption = correctAnswerNode.get("correctOption").asInt();

                String correctAnswerLabel = getCorrectOptionLabelInLanguage(detectedLanguage);
                String correctAnswerText = correctAnswerLabel + ": " + (char)('A' + correctOption - 1);
                PdfFont answerFont = getFontForText(correctAnswerText, devanagariFont, regularFont);

                Paragraph correctAnswerPara = new Paragraph(correctAnswerText)
                        .setFont(answerFont)
                        .setBold()
                        .setFontSize(12)
                        .setFontColor(new DeviceRgb(0, 128, 0)) // Green text
                        .setBackgroundColor(new DeviceRgb(240, 255, 240)) // Light green background
                        .setPadding(5)
                        .setPaddingLeft(10)
                        .setMarginBottom(5);
                document.add(correctAnswerPara);
            }

            // Explanation with subtle background
            if (question.getAnswerExplanation() != null && !question.getAnswerExplanation().isEmpty()) {
                String explanationLabel = getExplanationLabelInLanguage(detectedLanguage);
                String explanationText = question.getAnswerExplanation();
                PdfFont explanationFont = getFontForText(explanationText, devanagariFont, regularFont);

                Paragraph explanationPara = new Paragraph()
                        .add(new Paragraph(explanationLabel + ": ").setFont(explanationFont).setBold()
                                .setFontColor(new DeviceRgb(0, 102, 153))) // Blue label
                        .add(new Paragraph(explanationText).setFont(explanationFont)
                                .setFontColor(new DeviceRgb(51, 51, 51))) // Dark gray text
                        .setBackgroundColor(new DeviceRgb(248, 252, 255)) // Very light blue background
                        .setPadding(8)
                        .setPaddingLeft(10)
                        .setMarginBottom(5);
                document.add(explanationPara);
            }

            document.add(new Paragraph("\n"));
        } catch (Exception e) {
            logger.error("Error adding answer to document", e);
        }
    }

    /**
     * Add information section showing filter criteria used to generate the questions
     */
    private void addInformationSection(Document document, List<QuestionEntity> questions,
                                      com.mahaexam.openai.model.QuestionMetadata metadata,
                                      String language, PdfFont devanagariFont, PdfFont regularFont) {
        try {
            PdfFont infoFont = getFontForText("Information", devanagariFont, regularFont);

            // Information section title with professional styling
            Paragraph infoTitle = new Paragraph(getInfoSectionTitleInLanguage(language))
                    .setFont(infoFont)
                    .setFontSize(16)
                    .setBold()
                    .setFontColor(new DeviceRgb(0, 102, 51)) // Dark green
                    .setBackgroundColor(new DeviceRgb(240, 255, 240)) // Light green background
                    .setPadding(10)
                    .setMarginBottom(15)
                    .setMarginTop(5);
            document.add(infoTitle);

            // Create info table with border and colors
            Table infoTable = new Table(UnitValue.createPercentArray(new float[]{30, 70}))
                    .useAllAvailableWidth()
                    .setMarginBottom(25)
                    .setBackgroundColor(new DeviceRgb(250, 250, 250)); // Light gray background

            // Add names from metadata if provided
            if (metadata != null) {
                // ...existing metadata rows...
                if (metadata.getBoardName() != null && !metadata.getBoardName().isEmpty()) {
                    addInfoRow(infoTable, "Board", metadata.getBoardName(), infoFont);
                }
                if (metadata.getClassName() != null && !metadata.getClassName().isEmpty()) {
                    addInfoRow(infoTable, "Class", metadata.getClassName(), infoFont);
                }
                if (metadata.getSubject() != null && !metadata.getSubject().isEmpty()) {
                    addInfoRow(infoTable, "Subject", metadata.getSubject(), infoFont);
                }
                String medium = metadata.getMedium();
                if (medium == null || medium.isEmpty()) {
                    if (!questions.isEmpty() && questions.get(0).getMedium() != null) {
                        medium = questions.get(0).getMedium();
                    }
                }
                if (medium != null && !medium.isEmpty()) {
                    addInfoRow(infoTable, "Medium", medium, infoFont);
                }
                if (metadata.getChapter() != null && !metadata.getChapter().isEmpty()) {
                    addInfoRow(infoTable, "Chapter", metadata.getChapter(), infoFont);
                }
                if (metadata.getTopic() != null && !metadata.getTopic().isEmpty()) {
                    addInfoRow(infoTable, "Topic", metadata.getTopic(), infoFont);
                }
                if (metadata.getQuestionType() != null && !metadata.getQuestionType().isEmpty()) {
                    addInfoRow(infoTable, "Question Type", metadata.getQuestionType(), infoFont);
                }
                if (metadata.getSkillLevel() != null && !metadata.getSkillLevel().isEmpty()) {
                    addInfoRow(infoTable, "Skill Level", metadata.getSkillLevel(), infoFont);
                }
                if (metadata.getDifficulty() != null && !metadata.getDifficulty().isEmpty()) {
                    addInfoRow(infoTable, "Difficulty Level", metadata.getDifficulty(), infoFont);
                }
            }
            addInfoRow(infoTable, "Number of Questions", String.valueOf(questions.size()), infoFont);
            document.add(infoTable);
            document.add(new Paragraph("\n").setMarginBottom(10));
        } catch (Exception e) {
            logger.error("Error adding information section", e);
        }
    }

    /**
     * Helper method to add a row to the info table
     */
    private void addInfoRow(Table table, String label, String value, PdfFont font) {
        // Label cell with styling
        Cell labelCell = new Cell()
                .add(new Paragraph(label + ":").setFont(font).setBold())
                .setBackgroundColor(new DeviceRgb(245, 245, 245)) // Slightly darker gray
                .setFontColor(new DeviceRgb(51, 51, 51)) // Dark gray text
                .setPadding(8)
                .setPaddingLeft(12);
        table.addCell(labelCell);

        // Value cell with styling
        Cell valueCell = new Cell()
                .add(new Paragraph(value).setFont(font))
                .setFontColor(new DeviceRgb(0, 0, 0)) // Black text
                .setPadding(8)
                .setPaddingLeft(12);
        table.addCell(valueCell);
    }

    /**
     * Get information section title based on language
     */
    private String getInfoSectionTitleInLanguage(String language) {
        if (language == null) return "Question Paper Information";

        return switch (language.toLowerCase()) {
            case "hindi" -> "प्रश्न पत्र जानकारी";
            case "marathi" -> "प्रश्नपत्र माहिती";
            default -> "Question Paper Information";
        };
    }

    /**
     * Detect language from a list of questions by analyzing their text content
     * Checks question text, options, and explanations
     */
    private String detectLanguageFromQuestions(List<QuestionEntity> questions) {
        if (questions == null || questions.isEmpty()) {
            return "English";
        }

        // Count Devanagari characters across all questions
        int devanagariCount = 0;
        int totalChars = 0;

        for (QuestionEntity question : questions) {
            // Check question text
            if (question.getQuestionText() != null) {
                String text = question.getQuestionText();
                totalChars += text.length();
                devanagariCount += countDevanagariChars(text);
            }

            // Check options
            if (question.getOptions() != null) {
                try {
                    JsonNode optionsNode = objectMapper.readTree(question.getOptions());
                    for (int i = 1; i <= 4; i++) {
                        String optionKey = "option" + i;
                        if (optionsNode.has(optionKey)) {
                            String optionText = optionsNode.get(optionKey).asText();
                            totalChars += optionText.length();
                            devanagariCount += countDevanagariChars(optionText);
                        }
                    }
                } catch (Exception e) {
                    logger.debug("Error parsing options for language detection", e);
                }
            }

            // Check explanation
            if (question.getAnswerExplanation() != null) {
                String explanation = question.getAnswerExplanation();
                totalChars += explanation.length();
                devanagariCount += countDevanagariChars(explanation);
            }
        }

        // If more than 10% of characters are Devanagari, assume Hindi/Marathi
        if (totalChars > 0 && (devanagariCount * 100.0 / totalChars) > 10) {
            // Try to distinguish between Hindi and Marathi by checking for Marathi-specific characters
            // Marathi uses some unique characters like ळ (U+0933)
            for (QuestionEntity question : questions) {
                if (question.getQuestionText() != null && containsMarathiChars(question.getQuestionText())) {
                    return "Marathi";
                }
            }
            return "Hindi"; // Default to Hindi for Devanagari content
        }

        return "English";
    }

    /**
     * Detect language from a single text string
     */
    private String detectLanguageFromText(String text) {
        if (text == null || text.isEmpty()) {
            return "English";
        }

        int devanagariCount = countDevanagariChars(text);
        int totalChars = text.length();

        // If more than 10% of characters are Devanagari
        if (totalChars > 0 && (devanagariCount * 100.0 / totalChars) > 10) {
            // Check for Marathi-specific characters
            if (containsMarathiChars(text)) {
                return "Marathi";
            }
            return "Hindi";
        }

        return "English";
    }

    /**
     * Count the number of Devanagari characters in text
     */
    private int countDevanagariChars(String text) {
        if (text == null) return 0;
        return (int) text.codePoints()
                        .filter(cp -> (cp >= 0x0900 && cp <= 0x097F))
                        .count();
    }

    /**
     * Check if text contains Marathi-specific characters
     * Marathi uses some unique characters not common in Hindi
     */
    private boolean containsMarathiChars(String text) {
        if (text == null) return false;
        // Check for Marathi-specific characters:
        // ळ (U+0933) - Marathi 'La'
        // ऱ्ह (U+0931) - Marathi 'Ra'
        return text.codePoints().anyMatch(cp -> cp == 0x0933 || cp == 0x0931);
    }

    /**
     * Get title text based on detected language
     */
    private String getTitleInLanguage(String language) {
        if (language == null) return "Question Paper";

        return switch (language.toLowerCase()) {
            case "hindi" -> "प्रश्न पत्र";
            case "marathi" -> "प्रश्नपत्रिका";
            default -> "Question Paper";
        };
    }

    /**
     * Get answer key title based on detected language
     */
    private String getAnswerTitleInLanguage(String language) {
        if (language == null) return "Answer Key & Explanations";

        return switch (language.toLowerCase()) {
            case "hindi" -> "उत्तर कुंजी और स्पष्टीकरण";
            case "marathi" -> "उत्तर की आणि स्पष्टीकरण";
            default -> "Answer Key & Explanations";
        };
    }

    /**
     * Get answer label based on detected language
     */
    private String getAnswerLabelInLanguage(String language, int questionNumber) {
        if (language == null) return "Answer " + questionNumber;

        return switch (language.toLowerCase()) {
            case "hindi" -> "उत्तर " + questionNumber;
            case "marathi" -> "उत्तर " + questionNumber;
            default -> "Answer " + questionNumber;
        };
    }

    /**
     * Get correct option label based on detected language
     */
    private String getCorrectOptionLabelInLanguage(String language) {
        if (language == null) return "Correct Option";

        return switch (language.toLowerCase()) {
            case "hindi" -> "सही विकल्प";
            case "marathi" -> "योग्य पर्याय";
            default -> "Correct Option";
        };
    }

    /**
     * Get explanation label based on detected language
     */
    private String getExplanationLabelInLanguage(String language) {
        if (language == null) return "Explanation";

        return switch (language.toLowerCase()) {
            case "hindi" -> "स्पष्टीकरण";
            case "marathi" -> "स्पष्टीकरण";
            default -> "Explanation";
        };
    }
}


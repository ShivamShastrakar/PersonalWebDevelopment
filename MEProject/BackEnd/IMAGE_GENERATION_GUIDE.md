# Image Generation Improvements for Scholarship Exam Diagrams

## Overview
The image generation system has been significantly enhanced to produce high-quality, exam-style diagrams that match the style and quality of reference exam papers.

## Key Improvements

### 1. **Two-Stage Generation Process**
- **Stage 1**: GPT-4 Vision analyzes reference exam paper images
- **Stage 2**: DALL-E 3 generates images using enhanced prompts

### 2. **HD Quality Output**
- Upgraded from `standard` to `hd` quality
- Produces sharper, more detailed diagrams suitable for exam papers

### 3. **Reference Image Analysis**
- GPT-4 Vision examines your sample PDF diagrams
- Extracts style characteristics:
  - Line thickness and style (thin/thick, solid/dashed)
  - Grid spacing and axis markings
  - Label placement and font style
  - Arrow types and sizes
  - Layout proportions
  - Color scheme (black & white vs minimal color)

### 4. **Enhanced Prompt Engineering**
- Creates optimized DALL-E prompts based on reference analysis
- Focuses on technical accuracy and exam paper aesthetics
- Removes decorative elements and watermarks

## How It Works

### Method Signatures

```java
// Basic usage (without reference images)
public String generateAndUploadImage(String description) throws IOException

// Enhanced usage (with reference images for style matching)
public String generateAndUploadImage(String description, List<String> referenceImages) throws IOException
```

### Workflow

1. **Load Sample PDF**
   ```java
   List<String> base64Images = convertPdfToBase64Images(samplePdfPath);
   ```

2. **Generate MCQs with Reference Context**
   ```java
   String jsonResponse = callOpenAiChatCompletionWithImages(prompt, base64Images);
   ```

3. **Generate Images with Style Matching**
   ```java
   String imageUrl = generateAndUploadImage(imageDescription, base64Images);
   ```

## Image Generation Flow

```
Sample PDF → PDF to Base64 → GPT-4 Vision Analysis → Enhanced Prompt → DALL-E 3 HD → Exam-Quality Diagram
```

### Detailed Steps:

1. **convertPdfToBase64Images()**: Converts PDF pages to base64 encoded images
2. **createEnhancedPromptWithVision()**: Analyzes reference images and creates optimized prompt
3. **generateImageWithDallE()**: Generates high-quality diagram using DALL-E 3

## Usage Examples

### Example 1: Generating Math Diagram with Reference
```java
McqGenerator generator = new McqGenerator();

// Step 1: Prepare reference images
String samplePdfPath = "/path/to/sample_exam_paper.pdf";
List<String> referenceImages = generator.convertPdfToBase64Images(samplePdfPath);

// Step 2: Generate image with reference style
String description = "Coordinate grid showing point A at (2,3) and point B at (-1,4) with arrow showing translation";
String imageUrl = generator.generateAndUploadImage(description, referenceImages);
```

### Example 2: Generating Science Diagram
```java
String description = "Diagram of photosynthesis process showing chloroplast, sunlight, CO2 input, and O2 output with labeled arrows";
String imageUrl = generator.generateAndUploadImage(description, referenceImages);
```

### Example 3: Full MCQ Generation with Images
```java
generator.generateAndStoreMcqs(
    "Class 5",           // classLevel
    "Mathematics",       // subject
    "Geometry",          // chapter
    "Coordinate Geometry", // topic
    "English",           // medium
    "/path/to/sample_exam_paper.pdf" // samplePdfPath
);
```

## Best Practices

### 1. **Provide High-Quality Reference PDFs**
- Use official exam papers as references
- Ensure diagrams are clear and visible
- Include variety of diagram types

### 2. **Write Descriptive Image Descriptions**
Good:
```
"Coordinate grid with x-axis from -5 to 5, y-axis from -5 to 5. 
Show point P at (3,2) marked with solid dot. 
Arrow from origin to P labeled 'position vector'."
```

Bad:
```
"A coordinate system with a point"
```

### 3. **Optimize Reference Image Count**
- System uses up to 2 reference images for analysis
- Choose most relevant pages from sample PDF
- Balance between context and API token limits

### 4. **Image Description Guidelines**
- Be specific about coordinates, measurements, labels
- Mention line styles (solid, dashed, dotted)
- Specify arrow directions and types
- Include all text labels that should appear
- Mention grid/axis requirements

## API Configuration

### Current Settings:
```java
// GPT-4 Vision Settings
model: "gpt-4o"
max_tokens: 500
temperature: 0.3  // Lower for consistency
detail: "high"    // For reference images

// DALL-E 3 Settings
model: "dall-e-3"
size: "1024x1024"
quality: "hd"     // HD quality for clarity
style: "natural"  // Better for technical diagrams
```

## Troubleshooting

### Issue: Generated images don't match exam style
**Solution**: 
- Ensure reference PDF contains clear diagram examples
- Check that reference images are being passed correctly
- Review GPT-4 Vision generated prompts in console output

### Issue: Images are too artistic/decorative
**Solution**:
- The enhanced prompts specifically request technical diagrams
- Add more specific technical terms in descriptions
- Ensure "Technical diagram in exam paper style" prefix is present

### Issue: Text/labels are unclear
**Solution**:
- Upgrade to HD quality (already implemented)
- Specify exact text in description
- Mention font style preferences (sans-serif, simple)

### Issue: Diagram proportions are wrong
**Solution**:
- Include specific measurements in description
- Reference coordinate ranges explicitly
- Provide aspect ratio guidance

## Cost Considerations

### API Costs:
- **GPT-4 Vision**: ~$0.01-0.03 per image analysis (with 2 reference images)
- **DALL-E 3 HD**: $0.08 per image
- **Total per diagram**: ~$0.09-0.11

### Optimization Tips:
1. Reuse reference image analysis for multiple diagrams
2. Cache enhanced prompts for similar diagram types
3. Use standard quality for draft/testing phases

## Future Enhancements

### Potential Improvements:
1. **Prompt Caching**: Store and reuse successful prompts
2. **Post-Processing**: Add image enhancement filters
3. **Template Library**: Pre-built diagram templates
4. **Batch Generation**: Generate multiple variations
5. **Quality Validation**: Automatic diagram quality checks
6. **Style Transfer**: Direct style transfer from reference images

## Sample Output

### Console Output:
```
Loaded 3 images from PDF
Enhanced prompt from vision: Technical diagram in exam paper style: Clean coordinate grid, thin black lines on white background, x and y axes with arrows, point markers as solid circles, sans-serif labels, grid spacing 1 unit...
DALL-E Prompt: Technical diagram in exam paper style: Clean coordinate grid...
DALL-E Revised Prompt: A technical coordinate grid diagram...
Downloaded image: /tmp/mcq_image_1234567.png
```

## Code Structure

### Main Methods:
- `generateAndUploadImage(description)` - Basic generation
- `generateAndUploadImage(description, referenceImages)` - Enhanced with references
- `createEnhancedPromptWithVision(description, referenceImages)` - Vision analysis
- `createStandardExamPrompt(description)` - Fallback prompt
- `generateImageWithDallE(prompt)` - DALL-E 3 generation

### Helper Methods:
- `convertPdfToBase64Images(pdfPath)` - PDF processing
- `callOpenAiChatCompletionWithImages(prompt, images)` - GPT-4 Vision API

## Testing Recommendations

### Test Cases:
1. **Simple diagrams**: Points on coordinate grid
2. **Complex diagrams**: Multi-step processes with labels
3. **Geometric shapes**: Triangles, circles with measurements
4. **Scientific diagrams**: Process flows, cycles
5. **Mixed content**: Text + shapes + arrows

### Quality Checklist:
- [ ] Lines are clean and precise
- [ ] Text is readable and properly positioned
- [ ] No decorative elements or watermarks
- [ ] Matches reference exam paper style
- [ ] Mathematical/scientific accuracy
- [ ] Appropriate level for target class
- [ ] White background (no gradients)
- [ ] Professional appearance

## Support

For issues or questions:
1. Check console output for detailed error messages
2. Verify API keys and quotas
3. Review reference PDF quality
4. Test with simpler descriptions first
5. Examine generated prompts in logs

---

**Last Updated**: January 2026
**Version**: 2.0 (Enhanced with GPT-4 Vision)


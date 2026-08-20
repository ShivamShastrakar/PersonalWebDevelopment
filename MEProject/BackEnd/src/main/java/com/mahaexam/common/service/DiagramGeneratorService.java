package com.mahaexam.common.service;

import com.mahaexam.common.exception.ServiceException;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * Service for generating educational diagrams as SVG and PNG from text descriptions.
 * Uses OpenAI GPT-4 to generate SVG markup and Apache Batik for PNG conversion.
 */
@Service
public class DiagramGeneratorService {
    private static final Logger logger = LoggerFactory.getLogger(DiagramGeneratorService.class);
    @Value("${openai.apiKey}")
    private String apiKey;

    @Value("${openai.model:gpt-4o}")
    private String model;

    @Value("${openai.maxCompletionTokens:16384}")
    private int defaultMaxCompletionTokens;

    @Autowired
    private OpenAIRateLimiter rateLimiter;

    private final OkHttpClient client;

    /**
     * Create a new DiagramGeneratorService
     */
    public DiagramGeneratorService() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }


    /**
     * Generate a diagram from a text description
     * @param userPrompt Description of the diagram to generate
     * @return DiagramResult containing SVG and PNG data
     * @throws Exception if generation fails
     */
    public DiagramResult generateDiagram(String userPrompt)  {
        return generateDiagram(userPrompt, getDefaultSystemPrompt());
    }

    /**
     * Generate a diagram with a custom system prompt
     * @param userPrompt Description of the diagram to generate
     * @param systemPrompt Custom system prompt for diagram generation rules
     * @return DiagramResult containing SVG and PNG data
     * @throws Exception if generation fails
     */
    public DiagramResult generateDiagram(String userPrompt, String systemPrompt)  {
        logger.debug("Generating diagram for: " + userPrompt);

        // Generate SVG using OpenAI
        String svg = generateSVGFromAPI(userPrompt, systemPrompt);

        // Sanitize and enhance the SVG
        svg = sanitizeSVG(svg);
        svg = ensureAllTickMarks(svg, userPrompt);

        logger.info("SVG generation complete");
        byte[] pngData =null;
        /*
        // Convert to PNG
        pngData = convertSVGToPNG(svg);

        if (verbose) {
            System.out.println("PNG conversion complete");
        }
        */
        return new DiagramResult(svg, pngData);
    }

    /**
     * Generate a diagram and save it to files
     * @param userPrompt Description of the diagram to generate
     * @param svgOutputPath Path where SVG file should be saved
     * @param pngOutputPath Path where PNG file should be saved
     * @throws Exception if generation or file writing fails
     */
    public void generateAndSaveDiagram(String userPrompt, String svgOutputPath, String pngOutputPath) throws Exception {
        DiagramResult result = generateDiagram(userPrompt);

        // Save SVG
        Files.writeString(Path.of(svgOutputPath), result.getSvgContent());
        logger.info("SVG saved to " + svgOutputPath);
        /*
        // Save PNG
        try (FileOutputStream fos = new FileOutputStream(pngOutputPath)) {
            fos.write(result.getPngData());
        }
        if (verbose) {
            System.out.println("PNG saved to " + pngOutputPath);
        }
         */
    }

    /**
     * Generate SVG content from OpenAI API
     */
    private String generateSVGFromAPI(String userPrompt, String systemPrompt) {
        JSONObject payload = new JSONObject()
                .put("model", model)
                .put("messages", new JSONArray()
                        .put(new JSONObject()
                                .put("role", "system")
                                .put("content", systemPrompt))
                        .put(new JSONObject()
                                .put("role", "user")
                                .put("content", userPrompt)))
                .put("max_tokens", defaultMaxCompletionTokens)
                .put("temperature", 0.0);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(RequestBody.create(
                        payload.toString(),
                        MediaType.parse("application/json")))
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        logger.debug("Sending diagram request to OpenAI API...");

        String json;
        try {
            json = rateLimiter.executeWithRetry(() -> {
                try (Response response = client.newCall(request).execute()) {

                    int code = response.code();
                    if (code == 429 || code == 500 || code == 502 || code == 503
                            || code == 504 || (code >= 520 && code <= 527)) {
                        // Transient — signal for retry with back-off
                        ResponseBody errBody = response.body();
                        String snippet = errBody != null
                                ? errBody.string().replaceAll("<[^>]+>", "").replaceAll("\\s+", " ").trim()
                                : "(no body)";
                        if (snippet.length() > 300) snippet = snippet.substring(0, 300) + "…";
                        logger.warn("⚠️ OpenAI diagram API returned HTTP {} (transient). " +
                                "Will retry with back-off. Body snippet: {}", code, snippet);
                        throw new OpenAIRateLimiter.RateLimitException("HTTP " + code + " from OpenAI diagram API");
                    }
                    if (!response.isSuccessful()) {
                        ResponseBody errBody = response.body();
                        String snippet = errBody != null
                                ? errBody.string().replaceAll("<[^>]+>", "").replaceAll("\\s+", " ").trim()
                                : "(no body)";
                        if (snippet.length() > 300) snippet = snippet.substring(0, 300) + "…";
                        logger.error("Diagram API permanent error {}: {}", code, snippet);
                        throw new RuntimeException("Diagram API error " + code + ": " + snippet);
                    }
                    ResponseBody responseBody = response.body();
                    if (responseBody == null) {
                        throw new RuntimeException("Response body is null");
                    }
                    return responseBody.string();
                } catch (OpenAIRateLimiter.RateLimitException e) {
                    throw e; // let the retry wrapper handle it
                } catch (IOException e) {
                    logger.error("Error during diagram generation API request", e);
                    throw new ServiceException(e);
                }
            });
        } catch (OpenAIRateLimiter.RateLimitException e) {
            throw new RuntimeException("Diagram generation failed — OpenAI rate limit exceeded after all retries: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Diagram generation interrupted while waiting for rate limiter", e);
        }

        logger.debug("Received diagram response from API");

        JSONObject obj = new JSONObject(json);

        String svg = obj.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        // Strip BOM and normalize line endings
        svg = svg.replace("\uFEFF", "").replace("\r\n", "\n").replace("\r", "\n");

        // Always extract strictly between <svg ...> and </svg> — this handles:
        //  - markdown code fences (```svg ... ```)
        //  - leading prose / explanatory text from the model
        //  - trailing text after the closing tag
        int svgStart = svg.indexOf("<svg");
        int svgEnd   = svg.lastIndexOf("</svg>");
        if (svgStart == -1 || svgEnd == -1 || svgEnd <= svgStart) {
            logger.error("No valid <svg>...</svg> block found in API response. Raw content:\n{}", svg);
            throw new RuntimeException("OpenAI did not return a valid SVG block. Raw content: " + svg);
        }
        svg = svg.substring(svgStart, svgEnd + 6).trim();

        return svg;
    }



    /**
     * Sanitize SVG by fixing common issues
     */
    private String sanitizeSVG(String svg) {
        // Remove JavaScript template literals and invalid code patterns
        String sanitized = svg.replaceAll("\\$\\{[^}]*}", "<!-- invalid template literal removed -->");
        sanitized = sanitized.replaceAll("Array\\.from[^>]*>", "<!-- invalid JavaScript code removed -->");

        // Fix invalid orient attribute values in marker elements
        sanitized = sanitized.replaceAll("orient=\"auto-start-reverse\"", "orient=\"auto\"");

        // Remove polyline/polygon elements that contain percentages in points attribute
        sanitized = sanitized.replaceAll("<polyline[^>]*points=\"[^\"]*%[^\"]*\"[^>]*/>",
                "<!-- polyline with invalid percentage points removed -->");
        sanitized = sanitized.replaceAll("<polygon[^>]*points=\"[^\"]*%[^\"]*\"[^>]*/>",
                "<!-- polygon with invalid percentage points removed -->");

        // Fix duplicate y1 attributes (should be y2) in line elements
        sanitized = sanitized.replaceAll("(<line[^>]*y1=\"[^\"]+\"[^>]*) y1=\"([^\"]+)\"", "$1 y2=\"$2\"");

        // Fix duplicate x1 attributes (should be x2) in line elements
        sanitized = sanitized.replaceAll("(<line[^>]*x1=\"[^\"]+\"[^>]*) x1=\"([^\"]+)\"", "$1 x2=\"$2\"");

        // Ensure xmlns attribute is present on the root <svg> element
        if (sanitized.contains("<svg") && !sanitized.contains("xmlns=")) {
            sanitized = sanitized.replaceFirst("<svg", "<svg xmlns=\"http://www.w3.org/2000/svg\"");
        }

        // Add white background rectangle if not present
        int svgTagEnd = sanitized.indexOf(">", sanitized.indexOf("<svg"));
        if (svgTagEnd != -1 && !sanitized.contains("<rect") && !sanitized.contains("width=\"100%\" height=\"100%\" fill=\"#ffffff\"")) {
            String beforeContent = sanitized.substring(0, svgTagEnd + 1);
            String afterContent = sanitized.substring(svgTagEnd + 1);
            sanitized = beforeContent + "\n    <!-- White background -->\n    <rect width=\"100%\" height=\"100%\" fill=\"#ffffff\"/>" + afterContent;
        }

        // Final guard: strip anything before <svg or after </svg> that
        // sanitization steps may have inadvertently introduced
        int finalStart = sanitized.indexOf("<svg");
        int finalEnd   = sanitized.lastIndexOf("</svg>");
        if (finalStart != -1 && finalEnd != -1 && finalEnd > finalStart) {
            sanitized = sanitized.substring(finalStart, finalEnd + 6);
        }

        return sanitized;
    }

    /**
     * Extract X coordinates from text
     */
    private java.util.Set<Integer> extractXCoordinates(String text) {
        java.util.Set<Integer> coords = new java.util.TreeSet<>();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\((-?\\d+)\\s*,\\s*-?\\d+\\)");
        java.util.regex.Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            coords.add(Integer.parseInt(matcher.group(1)));
        }
        return coords;
    }

    /**
     * Extract Y coordinates from text
     */
    private java.util.Set<Integer> extractYCoordinates(String text) {
        java.util.Set<Integer> coords = new java.util.TreeSet<>();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\(-?\\d+\\s*,\\s*(-?\\d+)\\)");
        java.util.regex.Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            coords.add(Integer.parseInt(matcher.group(1)));
        }
        return coords;
    }

    /**
     * Add complete tick marks to coordinate grid diagrams
     */
    private String ensureAllTickMarks(String svg, String userPrompt) {
        // Check if this is a coordinate grid/plane diagram
        boolean hasAxisLines = svg.contains("x1=\"300\"") && svg.contains("y1=\"300\"");
        boolean hasCoordinateKeywords = userPrompt.toLowerCase().contains("coordinate") ||
                userPrompt.toLowerCase().contains("grid") ||
                userPrompt.toLowerCase().contains("axis") ||
                userPrompt.toLowerCase().contains("plane");

        boolean hasCoordinateGrid = hasAxisLines || hasCoordinateKeywords;

        if (!hasCoordinateGrid) {
            logger.info("No coordinate grid detected, skipping tick mark enhancement");
            return svg;
        }

        // Extract coordinates from user prompt
        java.util.Set<Integer> xCoords = extractXCoordinates(userPrompt);
        java.util.Set<Integer> yCoords = extractYCoordinates(userPrompt);

        if (xCoords.isEmpty() || yCoords.isEmpty()) {
            logger.info("No coordinates detected in prompt, skipping tick mark enhancement");
            return svg;
        }

        // Calculate range with padding
        int xMin = xCoords.stream().min(Integer::compare).orElse(0) - 3;
        int xMax = xCoords.stream().max(Integer::compare).orElse(0) + 3;
        int yMin = yCoords.stream().min(Integer::compare).orElse(0) - 3;
        int yMax = yCoords.stream().max(Integer::compare).orElse(0) + 3;

        // Always include 0
        if (xMin > 0) xMin = 0;
        if (xMax < 0) xMax = 0;
        if (yMin > 0) yMin = 0;
        if (yMax < 0) yMax = 0;

        logger.info("Detected coordinate ranges: X=[" + xMin + "," + xMax + "], Y=[" + yMin + "," + yMax + "]");

        // Remove any existing tick marks to avoid duplicates
        svg = svg.replaceAll("<line x1=\"\\d+\" y1=\"29[0-9]\" x2=\"\\d+\" y2=\"30[0-9]\"[^>]*/>\\s*", "");
        svg = svg.replaceAll("<line x1=\"\\d+\" y1=\"30[0-9]\" x2=\"\\d+\" y2=\"29[0-9]\"[^>]*/>\\s*", "");
        svg = svg.replaceAll("<line x1=\"29[0-9]\" y1=\"\\d+\" x2=\"30[0-9]\" y2=\"\\d+\"[^>]*/>\\s*", "");
        svg = svg.replaceAll("<line x1=\"30[0-9]\" y1=\"\\d+\" x2=\"29[0-9]\" y2=\"\\d+\"[^>]*/>\\s*", "");
        svg = svg.replaceAll("<text x=\"\\d+\" y=\"3[0-9]{2}\" font-family=\"Arial[^>]*font-size=\"1[0-6]\"[^>]*>-?\\d+</text>\\s*", "");
        svg = svg.replaceAll("<text x=\"3[0-9]{2}\" y=\"\\d+\" font-family=\"Arial[^>]*font-size=\"1[0-6]\"[^>]*>-?\\d+</text>\\s*", "");

        // Build tick marks
        StringBuilder xTickMarks = new StringBuilder("\n    <!-- X-axis tick marks (auto-generated) -->\n");
        for (int x = xMin; x <= xMax; x++) {
            int pixelX = 300 + (x * 40);
            if (pixelX >= 50 && pixelX <= 550) {
                xTickMarks.append(String.format("    <line x1=\"%d\" y1=\"295\" x2=\"%d\" y2=\"305\" stroke=\"#000000\" stroke-width=\"2\"/>\n", pixelX, pixelX));
                xTickMarks.append(String.format("    <text x=\"%d\" y=\"325\" font-family=\"Arial, sans-serif\" font-size=\"14\" text-anchor=\"middle\" fill=\"#000000\">%d</text>\n", pixelX, x));
            }
        }

        StringBuilder yTickMarks = new StringBuilder("    <!-- Y-axis tick marks (auto-generated) -->\n");
        for (int y = yMin; y <= yMax; y++) {
            int pixelY = 300 - (y * 40);
            if (pixelY >= 50 && pixelY <= 550) {
                yTickMarks.append(String.format("    <line x1=\"295\" y1=\"%d\" x2=\"305\" y2=\"%d\" stroke=\"#000000\" stroke-width=\"2\"/>\n", pixelY, pixelY));
                yTickMarks.append(String.format("    <text x=\"320\" y=\"%d\" font-family=\"Arial, sans-serif\" font-size=\"14\" fill=\"#000000\">%d</text>\n", pixelY + 5, y));
            }
        }

        // Insert tick marks before the closing </svg> tag
        int closingTag = svg.lastIndexOf("</svg>");
        if (closingTag != -1) {
            svg = svg.substring(0, closingTag) + xTickMarks.toString() + yTickMarks.toString() + svg.substring(closingTag);
        }

        return svg;
    }

    /**
     * Get the default system prompt for diagram generation
     */
    private String getDefaultSystemPrompt() {
        return """
You are an expert educational diagram generator for school exam papers.

Produce ONLY valid, well-formed SVG markup starting with <svg> and ending with </svg>.

CRITICAL RULES (APPLY TO ALL DIAGRAMS):
- Use ONLY absolute pixel coordinates (e.g., "250,100") - NEVER use percentages
- For polyline/polygon points, use format: "x1,y1 x2,y2 x3,y3" with NUMERIC values only
- Set width="600" height="600" on the root SVG element
- MUST include xmlns="http://www.w3.org/2000/svg" on the root <svg> element
- First element MUST be: <rect width="100%" height="100%" fill="#ffffff"/> for white background
- Black strokes only (#000000)
- White background (#ffffff) - use a rectangle element to ensure it renders
- Thin precise lines (stroke-width: 2)
- No shading or gradients
- No colors except black and white
- Simple sans-serif text (font-family: Arial, sans-serif)
- Mathematical accuracy is mandatory
- Use proper SVG syntax - all attributes must be valid
- NEVER use JavaScript, template literals like ${}, Array.from(), .map(), or any programming constructs
- Generate PURE SVG XML only - write out each element explicitly

SCALING AND SIZE REQUIREMENTS:
- Make the diagram LARGE and fill most of the 600x600 canvas
- Leave only 50-80 pixel margins on each side
- Points and shapes should be clearly visible with appropriate sizes
- Text labels should be 16-20px font size for good readability
- Make all elements BOLD and CLEAR - this is for educational purposes

=== COORDINATE GRID SPECIFIC RULES (ONLY apply if generating coordinate planes/grids) ===

COORDINATE AXIS REQUIREMENTS:
- Use a scale of 40 pixels per unit for coordinate systems (allows range of approximately -7.5 to +7.5)
- Place origin at center of canvas (300, 300 for 600x600 canvas)
- Draw x and y axes from edge to edge with minimal margins

CRITICAL COORDINATE TRANSFORMATION (MUST FOLLOW EXACTLY):
For a point with mathematical coordinates (x, y):
- pixel_x = origin_x + (x × scale) = 300 + (x × 40)
- pixel_y = origin_y - (y × scale) = 300 - (y × 40)
Note: Y-axis is INVERTED in SVG (positive y goes DOWN), so we SUBTRACT for the pixel y-coordinate

EXAMPLES OF CORRECT TRANSFORMATIONS (with scale=40):
- Point (2, 5): pixel_x = 300 + (2 × 40) = 380, pixel_y = 300 - (5 × 40) = 100
- Point (-1, 5): pixel_x = 300 + (-1 × 40) = 260, pixel_y = 300 - (5 × 40) = 100
- Point (-1, 7): pixel_x = 300 + (-1 × 40) = 260, pixel_y = 300 - (7 × 40) = 20
- Point (0, 0) [origin]: pixel_x = 300, pixel_y = 300

TICK MARKS FOR COORDINATE GRIDS:
- Include tick marks on both x and y axes (small lines perpendicular to axes)
- Label key tick marks with coordinate numbers
- For x-axis: tick at pixel_x = 300 + (n × 40) should be labeled with number n
- For y-axis: tick at pixel_y = 300 - (n × 40) should be labeled with number n
- Tick marks should be 8-10 pixels long
- Focus on tick marks relevant to the plotted points
- Label the origin as "0"
- Coordinate numbers should be placed near their tick marks (14-16px font size)
- Add axis labels (X and Y) at the ends of the axes

POINT AND PATH REQUIREMENTS (for coordinate diagrams):
- Mark points with filled circles (radius 6-8 pixels)
- Label each point with its coordinates, e.g., "(2,5)" in 18px font
- When showing movement/paths between points, draw connecting lines
- Add arrow markers to show direction of movement if requested
- Make all points clearly visible and properly labeled

=== END COORDINATE GRID SPECIFIC RULES ===

EXAMPLE OF VALID POLYLINE:
<polyline points="250,50 240,60 260,60" stroke="#000000" stroke-width="2" fill="none"/>

NOT VALID (DO NOT USE PERCENTAGES):
<polyline points="50%,10% 48%,12%" stroke="#000000" stroke-width="2" fill="none"/>
""";
    }

    /**
     * Result class containing generated diagram data
     */
    public static class DiagramResult {
        private final String svgContent;
        private final byte[] pngData;

        public DiagramResult(String svgContent, byte[] pngData) {
            this.svgContent = svgContent;
            this.pngData = pngData;
        }

        public String getSvgContent() {
            return svgContent;
        }

        public byte[] getPngData() {
            return pngData;
        }
    }
}

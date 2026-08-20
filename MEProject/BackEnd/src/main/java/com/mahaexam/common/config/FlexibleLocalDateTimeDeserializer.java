package com.mahaexam.common.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Custom deserializer that accepts both date and datetime formats for LocalDateTime fields
 * Supports formats:
 * - yyyy-MM-dd (date only - time will be set to 00:00:00)
 * - yyyy-MM-dd'T'HH:mm:ss (datetime)
 * - yyyy-MM-dd HH:mm:ss (datetime with space)
 */
public class FlexibleLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_WITH_SPACE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getText();

        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        dateString = dateString.trim();

        try {
            // Try parsing as full datetime with 'T'
            return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e1) {
            try {
                // Try parsing as datetime with space
                return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER_WITH_SPACE);
            } catch (DateTimeParseException e2) {
                try {
                    // Try parsing as date only, set time to start of day (00:00:00)
                    LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
                    return date.atStartOfDay();
                } catch (DateTimeParseException e3) {
                    throw new IOException("Unable to parse date/datetime: " + dateString +
                        ". Expected formats: yyyy-MM-dd or yyyy-MM-dd'T'HH:mm:ss", e3);
                }
            }
        }
    }
}

package com.mahaexam.common.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class RepoUtil {

	private RepoUtil() {
		// No Object Creation
	}

	public static Integer getOptionalInteger(ResultSet rs, String columnName) {
		try {
			// Check if column exists in ResultSet
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				if (metaData.getColumnName(i).equalsIgnoreCase(columnName)) {
					return rs.getObject(columnName, Integer.class); // Returns null if column value is NULL
				}
			}
			return null; // Column not found
		} catch (SQLException e) {
			// Log the exception if needed (optional)
			// e.g., LOGGER.warning("Column " + columnName + " not found: " +
			// e.getMessage());
			return null; // Return null if column is missing or an error occurs
		}
	}

	public static Long getOptionalLong(ResultSet rs, String columnName) {
		try {
			// Check if column exists in ResultSet
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				if (metaData.getColumnName(i).equalsIgnoreCase(columnName)) {
					return rs.getObject(columnName, Long.class); // Returns null if column value is NULL
				}
			}
			return null; // Column not found
		} catch (SQLException e) {
			// Log the exception if needed (optional)
			// e.g., LOGGER.warning("Column " + columnName + " not found: " +
			// e.getMessage());
			return null; // Return null if column is missing or an error occurs
		}
	}

	public static String getOptionalString(ResultSet rs, String columnName) {
		try {
			// Check if column exists in ResultSet
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				if (metaData.getColumnName(i).equalsIgnoreCase(columnName)) {
					return rs.getObject(columnName, String.class); // Returns null if column value is NULL
				}
			}
			return null; // Column not found
		} catch (SQLException e) {
			// Log the exception if needed (optional)
			// e.g., LOGGER.warning("Column " + columnName + " not found: " +
			// e.getMessage());
			return null; // Return null if column is missing or an error occurs
		}
	}

	public static Boolean getOptionalBoolean(ResultSet rs, String columnName) {
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				if (metaData.getColumnName(i).equalsIgnoreCase(columnName)) {
					Object value = rs.getObject(columnName);
					if (value == null)
						return null;
					if (value instanceof Boolean)
						return (Boolean) value;
					if (value instanceof Number)
						return ((Number) value).intValue() != 0;
					if (value instanceof String) {
						String str = ((String) value).trim();
						if (str.equalsIgnoreCase("true") || str.equals("1"))
							return true;
						if (str.equalsIgnoreCase("false") || str.equals("0"))
							return false;
					}
					// Fallback: not recognized
					return null;
				}
			}
			return null; // Column not found
		} catch (SQLException e) {
			return null;
		}
	}
}

package com.mahaexam.common.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
	private String errorCode;
	private int status;
	private String message;
	private String details;
	private LocalDateTime timestamp;
	private List<FieldError> fieldErrors;

	public ErrorResponse(int status, String details) {
		this.status = status;
		this.details = details;
		this.timestamp = LocalDateTime.now();
		this.fieldErrors = new ArrayList<>();
	}
	
	public ErrorResponse(int status, String details,String message) {
		this.status = status;
		this.details = details;
		this.message = message;
		this.timestamp = LocalDateTime.now();
		this.fieldErrors = new ArrayList<>();
	}

	public ErrorResponse(String errorCode, int status, String message, String details) {
		this.errorCode = errorCode;
		this.status = status;
		this.message = message;
		this.details = details;
		this.timestamp = LocalDateTime.now();
		this.fieldErrors = new ArrayList<>();
	}

	public ErrorResponse(String errorCode, int status, String message, String details, List<FieldError> fieldErrors) {
		this.errorCode = errorCode;
		this.status = status;
		this.message = message;
		this.details = details;
		this.timestamp = LocalDateTime.now();
		this.fieldErrors = fieldErrors != null ? fieldErrors : new ArrayList<>();
	}

	public static class FieldError {
		private String field;
		private String error;

		public FieldError(String field, String error) {
			this.field = field;
			this.error = error;
		}

		// Getters and Setters
		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
	}

	// Getters and Setters
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
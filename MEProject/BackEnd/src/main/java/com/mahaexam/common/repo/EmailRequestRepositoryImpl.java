package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.Attachment;
import com.mahaexam.common.model.EmailRequest;

@Repository
public class EmailRequestRepositoryImpl implements EmailRequestRepository {

	private final JdbcTemplate jdbcTemplate;

	public EmailRequestRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(EmailRequest emailRequest) {
		if (emailRequest.getId() == null) {
			String sql = "INSERT INTO email_requests (to_addresses, cc_addresses, bcc_addresses, subject, body, is_html, status) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(sql, emailRequest.getToAddresses(), emailRequest.getCcAddresses(),
					emailRequest.getBccAddresses(), emailRequest.getSubject(), emailRequest.getBody(),
					emailRequest.isHtml(), emailRequest.getStatus().name());
			Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
			emailRequest.setId(id);
			// Save attachments
			if (Objects.nonNull(emailRequest.getAttachments())) {
				for (Attachment attachment : emailRequest.getAttachments()) {
					if (attachment.getData() != null && attachment.getName() != null
							&& !attachment.getName().isEmpty()) {
						attachment.setEmailRequestId(id);
						saveAttachment(attachment);
					}
				}
			}
		} else

		{
			String sql = "UPDATE email_requests SET to_addresses = ?, cc_addresses = ?, bcc_addresses = ?, subject = ?, body = ?, is_html = ?, "
					+ "status = ?, created_at = ?, sent_at = ?, error_message = ? WHERE id = ?";
			jdbcTemplate.update(sql, emailRequest.getToAddresses(), emailRequest.getCcAddresses(),
					emailRequest.getBccAddresses(), emailRequest.getSubject(), emailRequest.getBody(),
					emailRequest.isHtml(), emailRequest.getStatus().name(), emailRequest.getCreatedAt(),
					emailRequest.getSentAt(), emailRequest.getErrorMessage(), emailRequest.getId());
			// Delete existing attachments and save new ones
			jdbcTemplate.update("DELETE FROM email_attachments WHERE email_request_id = ?", emailRequest.getId());
			if (Objects.nonNull(emailRequest.getAttachments())) {
				for (Attachment attachment : emailRequest.getAttachments()) {
					if (attachment.getData() != null && attachment.getName() != null
							&& !attachment.getName().isEmpty()) {
						attachment.setEmailRequestId(emailRequest.getId());
						saveAttachment(attachment);
					}
				}
			}
		}
	}

	@Override
	public List<EmailRequest> findByStatus(EmailRequest.EmailStatus status) {
		String sql = "SELECT * FROM email_requests WHERE status = ?";
		List<EmailRequest> emailRequests = jdbcTemplate.query(sql, new Object[] { status.name() },
				this::mapRowToEmailRequest);
		// Fetch attachments for each email request
		for (EmailRequest emailRequest : emailRequests) {
			emailRequest.setAttachments(findAttachmentsByEmailRequestId(emailRequest.getId()));
		}
		return emailRequests;
	}

	@Override
	public void saveAttachment(Attachment attachment) {
		String sql = "INSERT INTO email_attachments (email_request_id, attachment_data, attachment_name) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, attachment.getEmailRequestId(), attachment.getData(), attachment.getName());
		Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		attachment.setId(id);
	}

	@Override
	public List<Attachment> findAttachmentsByEmailRequestId(Long emailRequestId) {
		String sql = "SELECT * FROM email_attachments WHERE email_request_id = ?";
		return jdbcTemplate.query(sql, new Object[] { emailRequestId }, this::mapRowToAttachment);
	}

	private EmailRequest mapRowToEmailRequest(ResultSet rs, int rowNum) throws SQLException {
		EmailRequest emailRequest = EmailRequest.builder().build();
		emailRequest.setId(rs.getLong("id"));
		emailRequest.setToAddresses(rs.getString("to_addresses"));
		emailRequest.setCcAddresses(rs.getString("cc_addresses"));
		emailRequest.setBccAddresses(rs.getString("bcc_addresses"));
		emailRequest.setSubject(rs.getString("subject"));
		emailRequest.setBody(rs.getString("body"));
		emailRequest.setHtml(rs.getBoolean("is_html"));
		emailRequest.setStatus(EmailRequest.EmailStatus.valueOf(rs.getString("status")));
		emailRequest.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
		emailRequest.setSentAt(rs.getObject("sent_at", LocalDateTime.class));
		emailRequest.setErrorMessage(rs.getString("error_message"));
		return emailRequest;
	}

	private Attachment mapRowToAttachment(ResultSet rs, int rowNum) throws SQLException {
		Attachment attachment = Attachment.builder().build();
		attachment.setId(rs.getLong("id"));
		attachment.setEmailRequestId(rs.getLong("email_request_id"));
		attachment.setData(rs.getBytes("attachment_data"));
		attachment.setName(rs.getString("attachment_name"));
		return attachment;
	}
}
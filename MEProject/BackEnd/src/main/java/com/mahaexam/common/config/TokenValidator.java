package com.mahaexam.common.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.tenant.management.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
@Order(3)
public class TokenValidator extends OncePerRequestFilter {
	private static final Logger logger = LogManager.getLogger(TokenValidator.class);
	private final ObjectMapper objectMapper;
	private final UserService userService;
	public static final String JWT_SECRET = "your-secure-jwt-secret-key-256-bit-long-at-least";
	
    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

	@Value("${public.api.paths:/api/v1/auth/login/**,/api/public/**,/api/menus/user/**,/api/masterdata/**,/api/v1/health/**,"
			+ "/api/boards/**,/api/classes/**,/api/courses/**,/api/course-subject-group-mappings/**,"
			+ "/api/education-societies/**,/api/institutes/**,/api/subject-board-class-mappings/**,"
			+ "/api/subjects/**,/api/subject-groups/**,/api/subject-group-mappings/**,"
			+ "/api/teacher/register/**,/api/students/register/**,/api/v1/otp/generate/**,"
			+ "/api/v1/otp/validate/**,/api/rule-types/**,/api/channelpartner/cpregister/**,"
			+ "/api/chapter/**,/api/academic-experiences/**,/payu/payments/payu-webhook/**,"
			+ "/api/institutes/search/**}")
	private List<String> publicApiPaths;

	public TokenValidator(ObjectMapper objectMapper, UserService userService) {
		this.objectMapper = objectMapper;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String requestURI = request.getRequestURI();
		String method = request.getMethod();
		String origin = request.getHeader("Origin");
		logger.debug("Processing request: {} {}, {}", method, requestURI, authHeader);

		// Skip authentication for OPTIONS requests (CORS preflight)
		if ("OPTIONS".equalsIgnoreCase(method)) {
			logger.debug("Allowing OPTIONS request for CORS preflight: {}", requestURI);
			setCorsHeaders(response, origin);
			response.setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(request, response);
			return;
		}

		// Skip authentication for GET requests to public endpoints
		if ("GET".equalsIgnoreCase(method) && isPublicEndpoint(requestURI)) {
			logger.debug("Skipping authentication for public GET endpoint: {}", requestURI);
			setCorsHeaders(response, origin);
			filterChain.doFilter(request, response);
			return;
		}
		if ((authHeader != null && authHeader.startsWith("Bearer "))) {
			String jwt = authHeader.substring(7);
			try {
				Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwt).getBody();

				Long userId = Long.parseLong(claims.getSubject());
				logger.info("userId=" + userId);
				if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					// Set authentication in SecurityContext
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId,
							null, Collections.emptyList());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					Optional<UserBean> userOpt = userService.findById(userId, false);
					UserBean user = userOpt
							.orElseThrow(() -> new IllegalArgumentException("User not found, invalid request."));
					HttpSession session = request.getSession(true);
					session.setAttribute(AppConstants.USER_CONTEXT, user);
				}
			} catch (ExpiredJwtException e) {
				setCorsHeaders(response, origin);
				sendErrorResponse(response, "JWT token is expired", HttpStatus.UNAUTHORIZED);
				return;
			} catch (io.jsonwebtoken.SignatureException | MalformedJwtException e) {
				setCorsHeaders(response, origin);
				sendErrorResponse(response, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
				return;
			} catch (Exception e) {
				setCorsHeaders(response, origin);
				sendErrorResponse(response, "Error processing JWT token: " + e.getMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private void sendErrorResponse(HttpServletResponse response, String message, HttpStatus status) throws IOException {
		response.setStatus(status.value());
		response.setContentType("application/json");
		ErrorResponse errorResponse = new ErrorResponse(status.value() + "", 0, "", message);
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}

	private boolean isPublicEndpoint(String requestURI) {
		logger.debug("Checking if {} is a public endpoint. Public paths: {}", requestURI, publicApiPaths);
		for (String pattern : publicApiPaths) {
			if (pattern.endsWith("/**")) {
				String basePath = pattern.substring(0, pattern.length() - 3);
				if (requestURI.startsWith(basePath)) {
					logger.debug("Matched public endpoint pattern: {}", pattern);
					return true;
				}
			} else if (requestURI.equals(pattern)) {
				logger.debug("Matched exact public endpoint: {}", pattern);
				return true;
			}
		}
		logger.debug("No public endpoint match for: {}", requestURI);
		return false;
	}

	public void setCorsHeaders(HttpServletResponse response, String origin) {
		if (origin != null && Arrays.asList(allowedOrigins.split(",")).contains(origin.trim())) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type,X-Auth-Token,xsrf-token");
            response.setHeader("Access-Control-Expose-Headers", "xsrf-token");
            logger.debug("Set CORS headers for origin: {}", origin);
        } else {
            logger.warn("Invalid or missing Origin header: {}", origin);
        }
    }

}

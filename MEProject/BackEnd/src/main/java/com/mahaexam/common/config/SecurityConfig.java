package com.mahaexam.common.config;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

	@Value("${cors.allowed-origins}")
    private String allowedOrigins;
	
	
	private final TokenValidator tokenValidator;

	public SecurityConfig(TokenValidator tokenValidator) {
		this.tokenValidator = tokenValidator;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.info("Inside securityFilterChain()");
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
						// Public APIs accessible without token
						.requestMatchers("/api/v1/auth/login/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/forgot-password/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/reset-password/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/masterdata/**").permitAll()
						.requestMatchers("/api/v1/health/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/boards/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/classes/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/courses/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/course-subject-group-mappings/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/education-societies/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/institutes/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/subject-board-class-mappings/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/subjects/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/subject-groups/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/subject-group-mappings/**").permitAll()
						.requestMatchers("/api/teacher/register/**").permitAll()
						.requestMatchers("/api/students/register/**").permitAll()
						.requestMatchers("/api/v1/otp/generate/**").permitAll()
						.requestMatchers("/api/v1/otp/validate/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/rule-types/**").permitAll()
						.requestMatchers("/api/channelpartner/cpregister/**").permitAll()
						.requestMatchers("/api/chapter/**").permitAll().requestMatchers("/api/academic-experiences/**")
						.permitAll().requestMatchers("/payu/payments/payu-webhook/**").permitAll()
                        .requestMatchers("/api/webhooks/razorpay/**").permitAll()

						.requestMatchers(HttpMethod.POST, "/api/institutes/search/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/areas-of-interest/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/earnings").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/earnings/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/commissions/**").permitAll()

						// Protected APIs requiring specific roles or permissions
//						.requestMatchers("/api/v1/admin/**").hasRole("ADMIN").requestMatchers("/api/v1/teacher/**")
//						.hasAnyRole("TEACHER", "ADMIN").requestMatchers("/api/v1/protected/**")
//						.hasAnyAuthority("read:user", "ROLE_TEACHER", "ROLE_ADMIN")
						// All other APIs require authentication
						.anyRequest().authenticated())
				.exceptionHandling(ex -> ex.accessDeniedHandler((req, res, ex2) -> {
	                logger.error("Access denied for request: {} {}. Reason: {}", req.getMethod(), req.getRequestURI(), ex2.getMessage());
	                setCorsHeaders(res, req.getHeader("Origin"));
	                res.setStatus(HttpStatus.FORBIDDEN.value());
	                res.setContentType("application/json");
	                res.getWriter().write("{\"error\": \"Access denied: " + ex2.getMessage() + "\"}");
	            }))
				.addFilterBefore(tokenValidator, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		// Split comma-separated origins from properties
        Arrays.stream(allowedOrigins.split(","))
              .map(String::trim)
              .forEach(configuration::addAllowedOrigin);
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("Authorization");
		configuration.addAllowedHeader("Content-Type");
		configuration.addAllowedHeader("X-Auth-Token");
		configuration.addAllowedHeader("xsrf-token");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
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
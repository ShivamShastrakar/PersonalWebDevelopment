package com.mahaexam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class SpringbootAppApplication {
	private static final Logger logger = LogManager.getLogger(SpringbootAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAppApplication.class, args);
		logger.info("Maha Exam Started Succesfully...");
	}
}

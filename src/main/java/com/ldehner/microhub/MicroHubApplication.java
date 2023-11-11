package com.ldehner.microhub;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroHubApplication {
	private static final Logger logger = LogManager.getLogger(MicroHubApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MicroHubApplication.class, args);
		logger.info("Started Application");
		logger.info("Waiting for clients to connect");
	}

}

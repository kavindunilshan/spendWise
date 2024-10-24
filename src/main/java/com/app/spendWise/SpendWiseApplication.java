package com.app.spendWise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpendWiseApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpendWiseApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpendWiseApplication.class, args);
	}

}

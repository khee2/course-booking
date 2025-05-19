package com.ringle.coursebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CoursebookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoursebookingApplication.class, args);
	}

}

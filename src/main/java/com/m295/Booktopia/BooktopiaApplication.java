package com.m295.Booktopia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BooktopiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooktopiaApplication.class, args);
	}

}

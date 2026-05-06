package com.thenocturn.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.thenocturn.pos.entity")
@EnableJpaRepositories(basePackages = "com.thenocturn.pos.repository")

public class PosTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosTemplateApplication.class, args);
	}

}

package com.mahmutsahin.starter;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.mahmutsahin"})
@EnableJpaRepositories(basePackages = {"com.mahmutsahin"})
@ComponentScan(basePackages = {"com.mahmutsahin"})
@SpringBootApplication
public class AccountManagementStarter {

	public static void main(String[] args) {
		SpringApplication.run(AccountManagementStarter.class, args);
	}

}

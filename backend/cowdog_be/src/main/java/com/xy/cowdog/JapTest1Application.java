package com.xy.cowdog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.xy.*")
@ComponentScan(basePackages = { "com.xy.*" })
@EntityScan("com.xy.*")   
public class JapTest1Application {
	public static void main(String[] args) {
		SpringApplication.run(JapTest1Application.class, args);
	}

}
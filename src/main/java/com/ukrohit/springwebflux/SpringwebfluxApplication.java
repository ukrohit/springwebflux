package com.ukrohit.springwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.ukrohit.springwebflux.${sec}")
@EnableR2dbcRepositories(basePackages = "com.ukrohit.springwebflux.${sec}")
public class SpringwebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringwebfluxApplication.class, args);
	}

}

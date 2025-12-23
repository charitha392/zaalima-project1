package com.vaultcore.vaultcore_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VaultcoreBackendApplication {

	public static void main(String[] args) {
		System.out.println("Hello World");
		System.out.println("Local Path: " + System.getProperty("user.dir"));
		SpringApplication.run(VaultcoreBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> System.out.println("Successfully connected to MongoDB. Server running on localhost:8080");
	}

}

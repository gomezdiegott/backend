package com.lamagiadelazucar.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marca esta clase como el punto de entrada de Spring Boot
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		// Lanza la aplicación
		SpringApplication.run(BackendApplication.class, args);
	}

}

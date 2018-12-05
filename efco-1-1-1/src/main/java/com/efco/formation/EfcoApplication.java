package com.efco.formation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EfcoApplication {

	public static void main(String[] args) {
		System.out.println("Started_app");
		SpringApplication.run(EfcoApplication.class, args);
		
	}
}

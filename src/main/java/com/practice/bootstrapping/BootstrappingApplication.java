package com.practice.bootstrapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BootstrappingApplication {

	public static void main(final String[] args) {
		SpringApplication.run(BootstrappingApplication.class, args);
	}

	@PostConstruct
	void deletingAllFactsAboutTheBootstrapping(){
		System.out.println("BootstrappingApplication.class is ending... goodbye!");
	}
}

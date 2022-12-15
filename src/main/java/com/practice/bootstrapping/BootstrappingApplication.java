package com.practice.bootstrapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class BootstrappingApplication {

	public static void main(final String[] args) {
		SpringApplication.run(BootstrappingApplication.class, args);
	}

	@PostConstruct
	void deletingAllFactsAboutTheBootstrapping(){
		log.error("Post construct called on the BootstrappingApplication.class");
	}
}

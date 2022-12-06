package com.practice.bootstrapping;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootstrappingApplicationTests {

	@Test
	@Disabled("TODO: blank test")
	void contextLoads() {
	}
	
	// to perform the junit tests an h2 db dependency for scope runtime is added to pom
	// this is for the repository tests where we have to mock them
	

}

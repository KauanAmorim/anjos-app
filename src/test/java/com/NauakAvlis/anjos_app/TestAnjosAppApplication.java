package com.NauakAvlis.anjos_app;

import org.springframework.boot.SpringApplication;

public class TestAnjosAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(AnjosAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

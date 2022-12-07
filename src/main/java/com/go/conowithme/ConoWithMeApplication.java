package com.go.conowithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties
public class ConoWithMeApplication {

	public static void main(String[] args) {

		SpringApplication.run(ConoWithMeApplication.class, args);
	}

}


package com.ericsson.securityassessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class SecurityAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityAssessmentApplication.class);
	}
	
	@Bean
	@Scope("application")
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}

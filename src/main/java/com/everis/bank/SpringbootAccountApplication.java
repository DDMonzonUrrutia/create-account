package com.everis.bank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//
//@EnableCircuitBreaker
//@EnableEurekaClient
@SpringBootApplication
public class SpringbootAccountApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAccountApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}

}

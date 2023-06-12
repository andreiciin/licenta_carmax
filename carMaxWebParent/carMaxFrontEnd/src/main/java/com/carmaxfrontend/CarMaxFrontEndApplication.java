package com.carmaxfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.carmax.common.entity")
public class CarMaxFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarMaxFrontEndApplication.class, args);
	}

}

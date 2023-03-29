package com.carmaxbackend.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.carmax.common.entity", "com.carmaxbackend.admin.user"})
public class CarMaxBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarMaxBackEndApplication.class, args);
	}

}

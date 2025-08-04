package com.hms.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppointmentMicroserviceHmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentMicroserviceHmsApplication.class, args);
	}

}

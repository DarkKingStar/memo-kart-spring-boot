package com.sudipta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MemokartServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemokartServiceRegistryApplication.class, args);
	}

}

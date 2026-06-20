package com.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		System.out.println("============================================");
		System.out.println("Eureka corriendo en : http://localhost:8761");
		System.out.println("Dashboard: Usa esa url en tu navegador");
		System.out.println("============================================");
	}

}

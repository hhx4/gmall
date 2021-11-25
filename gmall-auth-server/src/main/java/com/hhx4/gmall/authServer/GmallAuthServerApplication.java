package com.hhx4.gmall.authServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class GmallAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallAuthServerApplication.class, args);
	}

}

package com.hhx4.gmall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GmallSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallSearchApplication.class, args);
	}

}

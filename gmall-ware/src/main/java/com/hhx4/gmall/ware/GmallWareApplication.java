package com.hhx4.gmall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableFeignClients("com.hhx4.gmall.ware")
@EnableDiscoveryClient
@SpringBootApplication
public class GmallWareApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallWareApplication.class, args);
	}

}

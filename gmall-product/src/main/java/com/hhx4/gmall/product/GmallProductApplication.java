package com.hhx4.gmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.hhx4.gmall.product.feign")
@EnableDiscoveryClient
@MapperScan("com.hhx4.gmall.product.dao")
@SpringBootApplication
public class GmallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallProductApplication.class, args);
	}

}

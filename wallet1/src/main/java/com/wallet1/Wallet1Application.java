package com.wallet1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableMongoAuditing
@EnableFeignClients
public class Wallet1Application {
	@Bean	
	public RestTemplate restTemplate() {
		
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(Wallet1Application.class, args);
	}

}

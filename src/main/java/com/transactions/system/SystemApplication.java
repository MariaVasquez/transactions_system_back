package com.transactions.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class SystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

}

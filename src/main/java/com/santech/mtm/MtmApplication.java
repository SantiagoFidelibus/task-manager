package com.santech.mtm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MtmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtmApplication.class, args);
	}

}

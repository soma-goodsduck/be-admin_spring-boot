package com.ducks.goodsduck.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@SpringBootApplication
@PropertySource("classpath:/application-s3.yml")
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}

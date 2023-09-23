package com.msbook;

import com.msbook.service.serviceImpl.ImageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class BookApplication implements CommandLineRunner {

	@Resource
	private ImageService imageService;


	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		imageService.init();
	}
}

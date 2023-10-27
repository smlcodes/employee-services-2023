package com.employee;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * Main Application class
 * 
 * @author Satya
 */
@SpringBootApplication
@Configuration
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.setBannerMode(Banner.Mode.OFF);

		// Start Application
		app.run(args);		
	}

	@EventListener(ApplicationReadyEvent.class)
	void onApplicationReady(ApplicationReadyEvent event) {
		Environment env = event.getApplicationContext().getEnvironment();
		log.info("Application Ready!! \n\tListening on address: {} and port: {} \n\tProfiles: {}", 
					env.getProperty("server.address"), 
					env.getProperty("server.port"),
					env.getActiveProfiles()
				);

		log.info("\n Swagger : http://localhost:"+env.getProperty("server.port")+"/empapp/swagger-ui/index.html");
	}

	@PostConstruct
	void afterStartup(){
		log.info("\n \nAfter StartUp");

	}
}

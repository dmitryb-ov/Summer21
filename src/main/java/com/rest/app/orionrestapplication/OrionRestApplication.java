package com.rest.app.orionrestapplication;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@Log4j
public class OrionRestApplication {

    public static void main(String[] args) {
        log.info("Start application");
        SpringApplication.run(OrionRestApplication.class, args);
    }

}

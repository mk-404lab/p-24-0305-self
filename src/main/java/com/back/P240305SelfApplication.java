package com.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class P240305SelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(P240305SelfApplication.class, args);
    }

}

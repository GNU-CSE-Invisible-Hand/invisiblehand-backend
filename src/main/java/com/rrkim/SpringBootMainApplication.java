package com.rrkim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class SpringBootMainApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringBootMainApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

}

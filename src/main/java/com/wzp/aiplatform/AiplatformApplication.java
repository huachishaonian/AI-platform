package com.wzp.aiplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AiplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiplatformApplication.class, args);
    }

}

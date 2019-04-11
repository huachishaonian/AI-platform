package com.wzp.aiplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.wzp.aiplatform.mapper")
public class AiplatformApplication {

    public static void main(String[] args) {
        //123
        SpringApplication.run(AiplatformApplication.class, args);
    }

}

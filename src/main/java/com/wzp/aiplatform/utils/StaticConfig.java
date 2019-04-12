package com.wzp.aiplatform.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by wzp on 2019-04-12 16:37
 */
@Configuration
@PropertySource(value = "classpath:static-config.properties", encoding = "UTF-8")
@Data
public class StaticConfig {

    @Value("${upfile.path}")
    private String uploadPath;
    @Value("${decompression.path}")
    private String decompressionPath;

}

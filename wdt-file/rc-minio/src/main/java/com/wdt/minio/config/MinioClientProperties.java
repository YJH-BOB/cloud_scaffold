package com.wdt.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioClientProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}

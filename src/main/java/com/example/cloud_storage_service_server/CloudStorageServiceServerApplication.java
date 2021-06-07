package com.example.cloud_storage_service_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.TimeZone;

@SpringBootApplication
public class CloudStorageServiceServerApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Omsk"));
        SpringApplication.run(CloudStorageServiceServerApplication.class, args);
    }

}

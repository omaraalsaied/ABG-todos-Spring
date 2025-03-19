package com.example.todoApp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class LicenseKeyConfig {
    @Value("${license.key.path}")
    private String licenseKeyFilePath;

    @Bean
    public String licenseKey() throws IOException {
        log.info("Reading license file path from properties: {}", licenseKeyFilePath);

        Path path = Paths.get(licenseKeyFilePath);
        try {
            return Files.readString(path).trim();
        } catch (IOException e) {
            log.error("Failed to read license file: {}", e.getMessage());
            throw new IOException("Failed to read license key file: " + e.getMessage(), e);
        }
    }

}

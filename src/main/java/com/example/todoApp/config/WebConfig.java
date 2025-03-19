package com.example.todoApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final ValidateLicenseInterceptor validateLicenseInterceptor;

    public WebConfig(ValidateLicenseInterceptor validateLicenseInterceptor) {
        this.validateLicenseInterceptor = validateLicenseInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validateLicenseInterceptor);
    }
}

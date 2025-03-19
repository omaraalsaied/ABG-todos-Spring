package com.example.todoApp.config;

import com.abg.commonlib.dtos.ApiResponseDto;
import com.example.todoApp.ValidateLicenseClient;
import com.example.todoApp.dto.LicenseValidateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * step 1: read the license key from the file
 * step 2: call to ALM using feign client
 * step 3: check the status code :
 * response.code == 200 -> valid, return true
 * response.code == 400 -> invalid, return false
 */

@Component
@Slf4j
public class ValidateLicenseInterceptor implements HandlerInterceptor {

    private final String licenseKey;
    private final ValidateLicenseClient validateLicenseClient;


    public ValidateLicenseInterceptor(@Qualifier("licenseKey") String licenseKey,
                                      @Lazy ValidateLicenseClient validateLicenseClient) {
        this.licenseKey = licenseKey;
        this.validateLicenseClient = validateLicenseClient;

    }

    private ApiResponseDto<Object> validateLicense() {
        LicenseValidateRequest licenseValidateRequest = new LicenseValidateRequest();
        licenseValidateRequest.setLicenseKey(licenseKey);
        return validateLicenseClient.validateLicense(licenseValidateRequest);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("request incoming on {}", request.getRequestURI());
        log.info("application's license {}", licenseKey);
        try {
            if (validateLicense().getCode().equals(HttpStatus.BAD_REQUEST.value())) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("License Key is Invalid or Expired");
                return false;
            }
            return HandlerInterceptor.super.preHandle(request, response, handler);
        } catch (Exception e) {
            log.error("couldn't communicate with license manager due to error: {}", e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(e.getMessage());
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

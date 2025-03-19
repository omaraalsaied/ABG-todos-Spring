package com.example.todoApp;

import com.example.todoApp.dto.LicenseValidateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.abg.commonlib.dtos.ApiResponseDto;


@FeignClient(name = "validate-license", url = "${license.validation.url}")
public interface ValidateLicenseClient {
    @PostMapping("/license/soft-validate")
    ApiResponseDto<Object> validateLicense(@RequestBody LicenseValidateRequest request);
}

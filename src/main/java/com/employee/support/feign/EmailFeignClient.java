package com.employee.support.feign;

import com.employee.api.v1.model.dto.EmailRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author satyakaveti on 15/05/24
 */
@Service
@FeignClient(name = "email", url = "${feign.url.email}", path = "/api/v1/") //name-Service Name, URL - URL of the service
public interface EmailFeignClient {
    @PostMapping("/send")
    ResponseEntity<String> sendEmail(EmailRequestDto emailRequestDto);

    @GetMapping("/get")
    ResponseEntity<String> getEmail();
}

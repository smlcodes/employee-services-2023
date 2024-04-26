package com.employee.service;

import com.employee.api.v1.model.dto.EmailRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * @author satyakaveti on 10/04/24
 */
public interface MicroService {


    public ResponseEntity<String> sendEmployeeDataMail(EmailRequestDto dto);

    ResponseEntity<String> getEmail();
}

package com.employee.service.impl;

import com.employee.api.v1.model.dto.EmailRequestDto;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.service.EmployeeService;
import com.employee.service.MicroService;
import com.employee.support.client.Resource;
import com.employee.support.client.RestResourceRepository;
import com.employee.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

/**
 * @author satyakaveti on 10/04/24
 */

@Service
@Slf4j
public class MicroServiceImpl implements MicroService {


    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    RestResourceRepository rest;

    @Override
    public ResponseEntity<String> sendEmployeeDataMail(EmailRequestDto emailRequestDto) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        emailRequestDto.setBody(EmailUtil.employeeMailTemplate(employees));

        try {
            Resource resource = Resource.builder()
                    .data(emailRequestDto)
                    .name("SEND_EMAIL")
                    .uri(new URI("http://localhost:8992/email-service/api/v1/send"))
                    .requestType(HttpMethod.POST)
                    .responseClass(String.class).build();

            ResponseEntity response = rest.exchange(resource);
          //  ResponseEntity response = rest.postForEntity(resource);
            log.info("Body : ", response.getBody());
            log.info("Status : ", response.getStatusCode());
            return response;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<String> getEmail() {
        try {
            Resource resource = Resource.builder()
                    .name("GET_EMAIL")
                    .uri(new URI("http://localhost:8992/email-service/api/v1/get"))
                    .requestType(HttpMethod.GET)
                    .responseClass(String.class).build();

            ResponseEntity response = rest.getForEntity(resource);
            log.info("Body : ", response.getBody());
            log.info("Status : ", response.getStatusCode());
            return response;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

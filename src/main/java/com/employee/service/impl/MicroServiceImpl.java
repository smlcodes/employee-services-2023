package com.employee.service.impl;

import com.employee.api.v1.model.dto.EmailRequestDto;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.service.EmployeeService;
import com.employee.service.MicroService;
import com.employee.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void sendEmployeeDataMail(EmailRequestDto dto) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        dto.setBody(EmailUtil.employeeMailTemplate(employees));

        //ToDo - RestTemplate / Apache Feign call based on Falg

    }

    @Override
    public void sendEmployeeDataMailWithAttachments(EmailRequestDto dto) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        dto.setBody(EmailUtil.employeeMailTemplate(employees));

        //ToDo - RestTemplate / Apache Feign call based on Falg

    }
}

package com.employee.service;

import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.api.v1.model.mapper.EmployeeMapper;
import com.employee.dao.entity.Employee;
import com.employee.dao.repository.EmployeeRepository;
import com.employee.service.impl.EmployeeServiceImpl;
import com.employee.support.feign.EmailFeignClient;
import com.employee.utils.ApplicationUtils;
import com.employee.utils.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author satyakaveti on 09/04/25
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmployeeServiceImpl.class})
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeMapper employeeMapper;

    @MockBean
    private EmailUtil emailUtil;

    @MockBean
    private EmailFeignClient emailFeignClient;

    @MockBean
    private TaskScheduler taskScheduler;

    @MockBean
    private ApplicationUtils applicationUtils;

    @MockBean
    private ObjectMapper objectMapper;

    @Test
    void testGetAllEmployeesReturnsEmptyList() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        when(employeeMapper.mapEntityListToDtoListForEmployee(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<EmployeeDto> result = employeeService.getAllEmployees();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetEmployeeByIdFound() {
        Long id = 1L;
        Employee entity = new Employee();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(entity));
        when(employeeMapper.toDto(entity)).thenReturn(new EmployeeDto());

        EmployeeDto result = employeeService.getEmployeeById(id);
        assertNotNull(result);
    }
}

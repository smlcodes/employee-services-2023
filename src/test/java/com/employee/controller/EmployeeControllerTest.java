package com.employee.controller;

import com.employee.api.v1.EmployeeController;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.api.v1.model.mapper.EmployeeMapper;
import com.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author satyakaveti on 09/04/25
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmployeeController.class})
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeMapper employeeMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Long employeeId = 1L;
        EmployeeDto mockDto = new EmployeeDto();
        when(employeeService.getEmployeeById(employeeId)).thenReturn(mockDto);

        mockMvc.perform(get("/api/v1/employee/{id}", employeeId))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/employee/all"))
                .andExpect(status().isOk());
    }
}

package com.employee.repository;

import com.employee.api.v1.model.mapper.EmployeeSearchResultMapper;
import com.employee.dao.entity.Employee;
import com.employee.dao.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author satyakaveti on 09/04/25
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {

    @MockBean
    private EmployeeSearchResultMapper employeeSearchResultMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    void testSaveAndFindEmployee() {
        Employee employee = new Employee();
        employee.setName("Test");
        employee.setCity("User");

        Employee saved = employeeRepository.save(employee);
        Optional<Employee> found = employeeRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Test", found.get().getName());
    }
}

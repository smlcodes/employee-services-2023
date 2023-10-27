package com.employee;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

//import com.employee.dao.entity.Employee;
//import com.employee.dao.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Application-specific configuration, which may include Beans, Initializations, etc.
 */
@Configuration
@Slf4j
public class ApplicationConfig { 

	/*
    @Bean
    CommandLineRunner bootstrapEntities(EmployeeRepository employeeRepository) {
        return args -> {
        	// Do Something ...
        	List<Employee> employees = Arrays.asList(
        			new Employee("Bob", "Dylan"),
        			new Employee("Omar", "Sherrif"),
        			new Employee("Ali", "Baba")
        	);
        	            
            log.debug("Initializer - Bootstrapping database");
            log.debug("Preloading " + employeeRepository.saveAll(employees));
        };
    }

	 */
}
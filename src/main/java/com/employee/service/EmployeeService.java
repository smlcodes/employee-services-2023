package com.employee.service;

import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.api.v1.model.dto.EmployeeSearchDto;
import com.employee.api.v1.model.dto.EmployeeSearchResultsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    Page<EmployeeSearchResultsDto> searchAllEmployee(Pageable pageRequest, EmployeeSearchDto searchCriteria);

    EmployeeDto save(EmployeeDto employeeDto, Long id);

    EmployeeDto getEmployeeById(Long id);

    void delete(Long id);

    List<EmployeeDto> getEmployeeHistoryById(Long id, Pageable pageable);

    void scheduleTask(String cron);

    List<EmployeeDto> getAllEmployees();

}


package com.employee.service;

import com.employee.api.v1.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<EmployeeSearchResultsDto> searchAllEmployee(Pageable pageRequest, EmployeeSearchDto searchCriteria);

    EmployeeDto save(EmployeeDto employeeDto, Long id);

    EmployeeDto getEmployeeById(Long id);

    void delete(Long id);

}

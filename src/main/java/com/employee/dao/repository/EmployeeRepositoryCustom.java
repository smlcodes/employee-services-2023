
package com.employee.dao.repository;

import com.employee.api.v1.model.dto.EmployeeSearchDto;
import com.employee.api.v1.model.dto.EmployeeSearchResultsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

 
public interface EmployeeRepositoryCustom {

	Page<EmployeeSearchResultsDto> employeeSearchCriteria(Pageable pageRequest, EmployeeSearchDto searchCriteria);
}

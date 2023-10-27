
package com.employee.api.v1.model.mapper;

import com.employee.api.v1.model.dto.EmployeeSearchResultsDto;
import com.employee.dao.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;

 
@Mapper(componentModel = "spring")
public interface EmployeeSearchResultMapper {

    List<EmployeeSearchResultsDto> toEmployeeSearchResultsDtoList(List<Employee> employeeEntityList);

    EmployeeSearchResultsDto toEmployeeSearchResultsDto(Employee employee);

}
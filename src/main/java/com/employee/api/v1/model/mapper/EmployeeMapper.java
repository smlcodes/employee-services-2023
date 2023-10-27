
package com.employee.api.v1.model.mapper;


import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.dao.entity.Employee;
import com.employee.api.v1.model.dto.*;
import com.employee.dao.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.*;

 
 /**
 * @author Satya Kaveti
 */


@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    void  toEntity(EmployeeDto employeeDto, @MappingTarget Employee target);

    EmployeeDto toDto(Employee entity);

    AccountDto mapEntityToDtoAccount(Account account);

    Account mapDtoToEntityAccount(AccountDto accountDtos);
 

    List<DocumentDto> mapEntityListToDtoListForDocument(List<Document> document);

    List<Document> mapDtoListToEntityListForDocument(List<DocumentDto> documentDtos);
 

    DocumentDto mapEntityToDtoDocument(Document document);

    Document mapDtoToEntityDocument(DocumentDto documentDtos);

}

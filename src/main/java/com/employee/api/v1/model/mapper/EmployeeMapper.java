
package com.employee.api.v1.model.mapper;


import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.dao.entity.Employee;
import com.employee.api.v1.model.dto.*;
import com.employee.dao.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.*;

 
 /**
 * @author Satya Kaveti
 */


@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends BaseMapper{
     @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "dateToLong")
     @Mapping(target = "modifiedDate", source = "modifiedDate", qualifiedByName = "dateToLong")
    void  toEntity(EmployeeDto employeeDto, @MappingTarget Employee target);


     @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "longToDate")
     @Mapping(target = "modifiedDate", source = "modifiedDate", qualifiedByName = "longToDate")
    EmployeeDto toDto(Employee entity);

    AccountDto mapEntityToDtoAccount(Account account);

    Account mapDtoToEntityAccount(AccountDto accountDtos);
 

    List<DocumentDto> mapEntityListToDtoListForDocument(List<Document> document);

    List<Document> mapDtoListToEntityListForDocument(List<DocumentDto> documentDtos);
 

    DocumentDto mapEntityToDtoDocument(Document document);

    Document mapDtoToEntityDocument(DocumentDto documentDtos);

}

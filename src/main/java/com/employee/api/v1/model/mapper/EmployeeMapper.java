package com.employee.api.v1.model.mapper;


import com.employee.api.v1.model.dto.AccountDto;
import com.employee.api.v1.model.dto.DocumentDto;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.dao.entity.Account;
import com.employee.dao.entity.Document;
import com.employee.dao.entity.Employee;
import org.mapstruct.*;

import java.util.Date;
import java.util.List;


/**
 * @author Satya Kaveti
 */


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends BaseMapper {
    @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "dateToLong")
    @Mapping(target = "modifiedDate", source = "modifiedDate", qualifiedByName = "dateToLong")
    void toEntity(EmployeeDto employeeDto, @MappingTarget Employee target);


    @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "longToDate")
    @Mapping(target = "modifiedDate", source = "modifiedDate", qualifiedByName = "longToDate")
    EmployeeDto toDto(Employee entity);

    List<EmployeeDto> mapEntityListToDtoListForEmployee(List<Employee> employees);

    AccountDto mapEntityToDtoAccount(Account account);

    Account mapDtoToEntityAccount(AccountDto accountDtos);


    List<DocumentDto> mapEntityListToDtoListForDocument(List<Document> document);

    List<Document> mapDtoListToEntityListForDocument(List<DocumentDto> documentDtos);


    DocumentDto mapEntityToDtoDocument(Document document);

    Document mapDtoToEntityDocument(DocumentDto documentDtos);

    @Named("longToDate")
    default Date mapLongToDate(Long value) {
        if (value == null) {
            return null;
        }
        return new Date(value);
    }

    @Named("dateToLong")
    default Long mapDateToLong(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime();
    }


}

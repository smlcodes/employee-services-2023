
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


@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseMapper {
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

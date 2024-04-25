
package com.employee.api.v1.model.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Date;


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

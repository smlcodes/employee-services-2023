package com.employee.api.v1.model.mapper;


import com.employee.api.v1.model.dto.UserDto;
import com.employee.api.v1.model.mapper.BaseMapper;
import com.employee.dao.entity.User;
import org.mapstruct.*;

import java.util.Date;
import java.util.List;


/**
 * @author Satya Kaveti
 */


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper {

    void toEntity(UserDto userDto, @MappingTarget User target);

    UserDto toDto(User entity);

    List<UserDto> mapEntityListToDtoListForUser(List<User> users);

 

 

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

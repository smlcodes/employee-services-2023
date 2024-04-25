package com.employee.api.v1.model.mapper;


import com.employee.api.v1.model.dto.UserDto;
import com.employee.dao.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Date;
import java.util.List;


/**
 * @author Satya Kaveti
 */


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper {

    void toEntity(UserDto userDto, @MappingTarget User target);

    UserDto toDto(User entity);

    List<UserDto> toUserDtoList(List<User> users);

    List<User> toUserEntityList(List<UserDto> users);

 

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

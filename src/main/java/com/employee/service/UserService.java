package com.employee.service;


import com.employee.api.v1.model.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDto save(UserDto employeeDto, Long id);

    UserDto getUserById(Long id);

    void delete(Long id);

    List<UserDto> getAllUsers();

}

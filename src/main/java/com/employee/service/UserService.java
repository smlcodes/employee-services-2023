package com.employee.service;


import com.employee.api.v1.model.dto.UserDto;
import org.springframework.batch.core.BatchStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDto save(UserDto employeeDto, Long id);

    UserDto getUserById(Long id);

    void delete(Long id);

    List<UserDto> getAllUsers();

    List<UserDto> uploadUsers(MultipartFile file);

    BatchStatus batchUploadUsers(MultipartFile file);
}

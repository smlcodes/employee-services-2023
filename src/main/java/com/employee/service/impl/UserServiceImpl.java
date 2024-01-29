package com.employee.service.impl;


import com.employee.api.v1.model.dto.UserDto;
import com.employee.api.v1.model.mapper.UserMapper;
import com.employee.dao.entity.User;
import com.employee.dao.repository.UserRepository;
import com.employee.exception.BusinessException;
import com.employee.exception.EntityNotFoundException;
import com.employee.service.UserService;
import com.employee.utils.UserCsvUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Satya Kaveti
 */


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Transactional()
    @Override
    public UserDto save(UserDto userDto, Long userId) {
        log.info("save start :::: userId:{}", userId);
        try {
            Boolean createRequest = StringUtils.isEmpty(userId) ? Boolean.TRUE : Boolean.FALSE;
            User user = getUser(userId, createRequest);
            userMapper.toEntity(userDto, user);
            User entity = userRepository.save(user);
            return userMapper.toDto(entity);
        } catch (Exception ex) {
            log.error("Error while saving user", ex);
            throw new BusinessException("Save Failed");
        }
    }

    private User getUser(Long userId, Boolean isCreateRequest) {
        User user = isCreateRequest ? new User() : this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, userId));
        return user;
    }

    @Override
    public UserDto getUserById(Long id) {
        var entity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, id));
        return userMapper.toDto(entity);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserDtoList(users);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long userId) {
        try {
            var entity = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, userId));
            userRepository.delete(entity);
        } catch (Exception ex) {
            log.error("Error while deleting", ex);
            throw ex;
        }
    }

    @Override
    public List<UserDto> uploadUsers(MultipartFile file) {
        List<UserDto> userDtos = null;
        try {
            userDtos = UserCsvUtil.importCsv(file.getInputStream());
            List<User> users = userRepository.saveAll(userMapper.toUserEntityList(userDtos));
            userDtos = userMapper.toUserDtoList(users);
        } catch (IOException e) {
            throw new BusinessException("Bulk Upload Filed : " + e.getMessage());
        }
        return userDtos;
    }


    @Override
    public BatchStatus batchUploadUsers(MultipartFile file) {
        try {
            // Save the uploaded file to a temporary location
            Path tempFile = Files.createTempFile("temp_users", ".csv");
            file.transferTo(tempFile.toFile());

            // Set the file path in the job parameters
            Map<String, JobParameter> maps = new HashMap<>();
            maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
            maps.put("inputFilePath", new JobParameter(tempFile.toAbsolutePath().toString()));
            JobParameters parameters = new JobParameters(maps);

            // Run the job with the updated parameters
            JobExecution jobExecution = jobLauncher.run(job, parameters);

            // Clean up the temporary file (optional)
            Files.delete(tempFile);

            return jobExecution.getStatus();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}


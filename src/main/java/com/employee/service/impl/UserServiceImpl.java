package com.employee.service.impl;


import com.employee.api.v1.model.dto.UserDto;
import com.employee.api.v1.model.mapper.UserMapper;
import com.employee.dao.entity.User;
import com.employee.dao.repository.UserRepository;
import com.employee.exception.BusinessException;
import com.employee.exception.EntityNotFoundException;
import com.employee.service.UserService;
import com.employee.utils.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
        return userMapper.mapEntityListToDtoListForUser(users);
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
 
 

}


package com.employee.batch;

import com.employee.api.v1.model.dto.UserDto;
import com.employee.dao.entity.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author satyakaveti
 */
@Component
public class UserItemProcessor implements ItemProcessor<UserDto, User> {
    @Override
    public User process(UserDto userDto) throws Exception {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
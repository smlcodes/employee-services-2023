package com.employee.batch;

import com.employee.dao.entity.User;
import com.employee.dao.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author satyakaveti
 */
@Component
public class UserItemWriter implements ItemWriter<User> {

    private UserRepository userRepository;

    @Autowired
    public UserItemWriter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void write(List<? extends User> users) throws Exception{
        System.out.println("Data Saved for Users: " + users);
        userRepository.saveAll(users);
    }
}

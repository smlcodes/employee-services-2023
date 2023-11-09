package com.employee.api.v1;


import com.employee.ApplicationConstants;
import com.employee.api.v1.model.dto.UserDto;
import com.employee.api.v1.model.mapper.UserMapper;
import com.employee.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author Satya Kaveti
 */

@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "user")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    private final ObjectMapper objectMapper;

    private final UserMapper userMapper;


    @ApiOperation("Create a new User or update exist ")
    @PostMapping
    public UserDto save(@RequestParam(name = "id", required = false) Long id, @RequestBody @Valid UserDto userDto) {
        return userService.save(userDto, id);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @ApiOperation("Get User Version History By Id")
    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }


    @ApiOperation("Deletes one or more User from the system by their Id's. Always returns 200.")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
    }

}
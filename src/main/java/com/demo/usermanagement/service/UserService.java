package com.demo.usermanagement.service;

import java.util.List;

import com.demo.usermanagement.model.User;
import com.demo.usermanagement.model.UserDto;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
}

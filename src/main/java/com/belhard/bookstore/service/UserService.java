package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers(int page, int items, String sortColumn, String direction);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getUserByLastName(String lastName);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto user);

    void deleteUser(Long id);

    boolean validateUser(String email, String password);

    Long countAllUsers();
}

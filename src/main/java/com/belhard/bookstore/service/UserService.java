package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers(Pageable pageable);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getUserByLastName(String lastName);

    UserDto saveUser(UserDto userDto);

    void deleteUser(Long id);

    boolean validateUser(String email, String password);

    Long countAllUsers();
}

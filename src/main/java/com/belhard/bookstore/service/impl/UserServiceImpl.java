package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.entity.User;
import com.belhard.bookstore.dao.repository.UserRepository;
import com.belhard.bookstore.exceptions.UserException;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.hql.internal.QueryExecutionRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;

    public UserServiceImpl() {
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.debug("Call method getAllUsers");
        Iterable<User> users = userRepository.findUsersByDeletedFalse();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(toDto(user));
        }
        return userDtos;
    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(UserDto.Role.valueOf(user.getRole().toString()));
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setBirthday(user.getBirthday());
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        logger.debug("Call method getUserById");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("There is no book with id " + id));
        return toDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.debug("Call method getUserByEmail");
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserException("There is no user with email " + email));
        return toDto(user);
    }

    @Override
    public List<UserDto> getUserByLastName(String lastName) {
        logger.debug("Call method getUserByLastName");
        Iterable<User> users = userRepository.findUsersByLastName(lastName);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(toDto(user));
        }
        return userDtos;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        logger.debug("Call method createUser");
        try {
            User createdUser = userRepository.save(toUser(userDto));
            UserDto createdUserDto = toDto(createdUser);
            return getUserById(createdUserDto.getId());
        } catch (IllegalArgumentException e) {
            throw new UserException("The user is not created");
        }
    }

    private User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setRole(User.Role.valueOf(userDto.getRole().toString()));
        if (userDto.getEmail().matches("\\w+@[a-z]+\\.[a-z]+")) {
            user.setEmail(userDto.getEmail());
        } else {
            throw new UserException("Illegal input of email.");
        }
        user.setPassword(userDto.getPassword());
        user.setBirthday(userDto.getBirthday());
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        logger.debug("Call method deleteUser");
        try {
            userRepository.softDelete(id);
        } catch (QueryExecutionRequestException e) {
            throw new UserException("The user with id " + id + " is not created.");
        }
    }

    @Override
    public boolean validateUser(String email, String password) {
        logger.debug("Call method validateUser");
        UserDto userDto = getUserByEmail(email);
        if (userDto == null) {
            return false;
        }
        return userDto.getPassword().equals(password);

    }

    @Override
    public Long countAllUsers() {
        logger.debug("Call method countAllUsers");
        return userRepository.countUsers();
    }
}

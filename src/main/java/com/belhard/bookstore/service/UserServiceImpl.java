package com.belhard.bookstore.service;

import com.belhard.bookstore.dao.User;
import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.exceptions.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl( UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.debug("Call method getAllUsers");
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userDao.getAllUsers();
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
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new UserException("There is no user with id = " + id);
        }
        return toDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.debug("Call method getUserByEmail");
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new UserException("There is no user with email = " + email);
        }
        return toDto(user);
    }

    @Override
    public List<UserDto> getUserByLastName(String lastName) {
        logger.debug("Call method getUserByLastName");
        List<User> users = userDao.getUserByLastName(lastName);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(toDto(user));
        }
        return userDtos;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.debug("Call method createUser");
        User userToCreate = toUser(userDto);
        User existing = userDao.getUserByEmail(userToCreate.getEmail());
        if (existing != null) {
            throw new UserException("This user is already exist.");
        }
        User createdUser = userDao.createUser(userToCreate);
        if (createdUser == null) {
            throw new UserException("The user is not created");
        }
        UserDto createdUserDto = toDto(createdUser);
        return getUserById(createdUserDto.getId());
    }

    private User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setRole(User.Role.valueOf(userDto.getRole().toString()));
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setBirthday(userDto.getBirthday());
        return user;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        logger.debug("Call method updateUser");
        User userToUpdate = toUser(userDto);
        User existing = userDao.getUserByEmail(userToUpdate.getEmail());
        if (existing != null && !existing.getEmail().equals(userDto.getEmail())) {
            throw new UserException("You can't update this user. User with email " + userDto.getEmail() + " is already exist");
        }
        User updatedUser = userDao.updateUser(userToUpdate);
        if (updatedUser == null) {
            throw new UserException("The user is not updated.");
        }
        UserDto updatedUserDto = toDto(updatedUser);
        return getUserById(updatedUserDto.getId());
    }

    @Override
    public void deleteUser(Long id) {
        logger.debug("Call method deleteUser");
        if (!userDao.deleteUser(id)) {
            throw new UserException("The user is not deleted");
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
    public int countAllUsers() {
        logger.debug("Call method countAllUsers");
        return userDao.countAllUsers();
    }
}

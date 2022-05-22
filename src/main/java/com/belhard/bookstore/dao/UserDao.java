package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getUserByLastName(String lastName);

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUser(Long id);

    int countAllUsers();
}

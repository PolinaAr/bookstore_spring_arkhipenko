package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.entity.User;

import java.util.List;

public interface UserDao extends AbstractDao<User, Long> {

    User getUserByEmail(String email);

    List<User> getUserByLastName(String lastName);

    Long countAllUsers();
}

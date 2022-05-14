package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserDto;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UsersCommand implements Command {

    private final UserService USER_SERVICE = new UserServiceImpl();

    public String execute(HttpServletRequest req) {
        List<UserDto> userDtos = USER_SERVICE.getAllUsers();
        req.setAttribute("users", userDtos);
        return "jsp/user/getAllUsers.jsp";
    }
}

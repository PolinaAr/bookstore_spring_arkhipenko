package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserDto;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("users")
public class UsersCommand implements Command {

    private UserService userService;

    public UsersCommand() {
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String execute(HttpServletRequest req) {
        List<UserDto> userDtos = userService.getAllUsers();
        req.setAttribute("users", userDtos);
        return "jsp/user/getAllUsers.jsp";
    }
}

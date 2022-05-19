package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserDto;
import com.belhard.bookstore.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("users")
public class UsersCommand implements Command {

    private final UserService userService;

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    public String execute(HttpServletRequest req) {
        List<UserDto> userDtos = userService.getAllUsers();
        req.setAttribute("users", userDtos);
        return "jsp/user/getAllUsers.jsp";
    }
}

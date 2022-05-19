package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserDto;
import com.belhard.bookstore.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("user")
public class UserCommand implements Command {

    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        UserDto userDto = userService.getUserById(id);
        if (userDto == null) {
            req.setAttribute("message", "no user with id: " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("user", userDto);
        return "jsp/user/getUser.jsp";

    }
}



package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserDto;
import com.belhard.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("userCommand")
public class UserCommand implements Command {

    private UserService userService;

    public UserCommand() {
        System.out.println("Create constructor userCommand");
    }

    @Autowired
    public void setUserService(UserService userService) {
        System.out.println("SET userService to userController");
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



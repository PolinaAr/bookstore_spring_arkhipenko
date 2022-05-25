package com.belhard.bookstore.controller;

import com.belhard.bookstore.exceptions.UserException;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDto> userDtos = userService.getAllUsers();
        model.addAttribute("users", userDtos);
        return "getAllUsers";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable Long id) {
        try {
            UserDto userDto = userService.getUserById(id);
            model.addAttribute("user", userDto);
            return "getUser";
        } catch (UserException e) {
            return "error";
        }
    }
}



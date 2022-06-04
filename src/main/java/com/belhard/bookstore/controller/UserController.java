package com.belhard.bookstore.controller;

import com.belhard.bookstore.exceptions.UserException;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        return "user/users";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable Long id) {
        try {
            UserDto userDto = userService.getUserById(id);
            model.addAttribute("user", userDto);
            return "user/getUser";
        } catch (UserException e) {
            model.addAttribute("message", "This user is not found.");
            return "error";
        }
    }

    @GetMapping("/create")
    public String createForm() {
        return "user/createUser";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(Model model, @RequestParam Map<String, Object> params) {
        try {
            UserDto userDto = setUserDto(params);
            UserDto created = userService.createUser(userDto);
            model.addAttribute("user", created);
            return "user/getUser";
        } catch (UserException e) {
            model.addAttribute("message", "This user is not created.");
            return "error";
        }

    }

    private UserDto setUserDto(Map<String, Object> params) {
        UserDto userDto = new UserDto();
        userDto.setName(params.get("name").toString());
        userDto.setLastName(params.get("lastname").toString());
        userDto.setRole(UserDto.Role.valueOf(params.get("role").toString().toUpperCase()));
        userDto.setEmail(params.get("email").toString());
        userDto.setPassword(params.get("password").toString());
        userDto.setBirthday(LocalDate.parse(params.get("birthday").toString()));
        return userDto;
    }

    @GetMapping("/edit/{id}")
    public String updateForm(Model model, @PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        model.addAttribute("user", userDto);
        return "user/updateUser";
    }

    @PostMapping("/{id}")
    public String updateUser(Model model, @PathVariable Long id, @RequestParam Map<String, Object> params) {
        try {
            UserDto userDto = setUserDto(params);
            userDto.setId(id);
            UserDto updated = userService.updateUser(userDto);
            model.addAttribute("user", updated);
            return "user/getUser";
        } catch (UserException e) {
            model.addAttribute("message", "User is not updated");
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable Long id) {
        try {
            userService.deleteUser(id);
            model.addAttribute("message", "The user with id " + id + " is successfully deleted.");
            return "delete";
        } catch (UserException e) {
            model.addAttribute("message", "The user is not deleted.");
            return "error";
        }
    }

}



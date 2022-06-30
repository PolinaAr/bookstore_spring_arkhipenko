package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.controller.util.ParamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private ParamReader paramReader;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setParamReader(ParamReader paramReader) {
        this.paramReader = paramReader;
    }

    @GetMapping
    public String getAllUsers(Model model, @RequestParam Map<String, Object> params) {
        Pageable pageable = paramReader.getPageable(params);
        List<UserDto> userDtos = userService.getAllUsers(pageable);
        model.addAttribute("users", userDtos);
        return "user/users";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        model.addAttribute("user", userDto);
        return "user/getUser";
    }

    @GetMapping("/create")
    public String createForm() {
        return "user/createUser";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(Model model, @RequestParam Map<String, Object> params, HttpSession session) {
        UserDto userDto = setUserDto(params);
        UserDto created = userService.saveUser(userDto);
        session.setAttribute("user", created);
        return "index";

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
        UserDto userDto = setUserDto(params);
        userDto.setId(id);
        UserDto updated = userService.saveUser((userDto));
        model.addAttribute("user", updated);
        return "user/getUser";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable Long id) {
        userService.deleteUser(id);
        model.addAttribute("message", "The user with id " + id + " is successfully deleted.");
        return "delete";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam String email, @RequestParam String password,
                        HttpSession session) {
        if (userService.validateUser(email, password)) {
            session.setAttribute("user", userService.getUserByEmail(email));
            return "index";
        } else {
            model.addAttribute("message", "The user is not logged in");
            return "error";
        }
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        return "index";
    }

}



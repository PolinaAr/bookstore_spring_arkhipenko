package com.belhard.bookstore.interceptors;

import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UsersAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        if (userDto == null) {
            response.sendRedirect("/users/login");
            return false;
        }
        if (userDto.getRole().equals(UserDto.Role.ADMIN)) {
            return true;
        }
        response.sendError(401);
        return false;
    }
}

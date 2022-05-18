package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("error")
public class ErrorCommand implements Command {

    public ErrorCommand() {
    }

    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("message", "Ooops...");
        return "jsp/error.jsp";
    }
}

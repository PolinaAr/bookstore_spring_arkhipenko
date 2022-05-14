package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("message", "Ooops...");
        return "jsp/error.jsp";
    }
}

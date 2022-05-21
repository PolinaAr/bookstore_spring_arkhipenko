package com.belhard.bookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component("error")
public class ErrorCommand {

    public ErrorCommand() {
    }

    public String execute(HttpServletRequest req) {
        req.setAttribute("message", "Ooops...");
        return "jsp/error.jsp";
    }
}

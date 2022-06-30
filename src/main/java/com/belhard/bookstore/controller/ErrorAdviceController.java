package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.exceptions.BookException;
import com.belhard.bookstore.service.exceptions.CreatingException;
import com.belhard.bookstore.service.exceptions.DeleteException;
import com.belhard.bookstore.service.exceptions.NullResultException;
import com.belhard.bookstore.service.exceptions.OrderException;
import com.belhard.bookstore.service.exceptions.UserException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorAdviceController {

    @ExceptionHandler
    public String handle(Model model, CreatingException e) {
        model.addAttribute("message", "Oops... error during creation. Please, try again.");
        return "error";
    }

    @ExceptionHandler
    public String handle(Model model, NullResultException e) {
        model.addAttribute("message", "There is no results with such parameter.");
        return "error";
    }

    @ExceptionHandler
    public String handle(Model model, DeleteException e) {
        model.addAttribute("message", "Oops... error during deleting.");
        return "error";
    }

    @ExceptionHandler
    public String handle(Model model, UserException e) {
        model.addAttribute("message", "Hmm.. something wrong with this account. We're working on it");
        return "error";
    }

    @ExceptionHandler
    public String handle(Model model, BookException e) {
        model.addAttribute("message", "Hmm.. something wrong with this book. We're working on it");
        return "error";
    }

    @ExceptionHandler
    public String handle(Model model, OrderException e) {
        model.addAttribute("message", "Hmm.. something wrong with this order. We're working on it");
        return "error";
    }
}

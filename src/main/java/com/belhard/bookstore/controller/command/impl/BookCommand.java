package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.exceptions.BookException;
import com.belhard.bookstore.service.BookDto;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@Repository("bookCommand")
public class BookCommand implements Command {

    private BookService bookService;

    public BookCommand() {
        System.out.println("Create constructor BookCommand");
    }

    @Autowired
    public void setBookService(BookService bookService) {
        System.out.println("SET bookService to BookCommand");
        this.bookService = bookService;
    }

    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        BookDto bookDto = bookService.getBookById(id);
        if (bookDto == null) {
            req.setAttribute("message", "no book with id: " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("book", bookDto);
        return "jsp/book/getBook.jsp";
    }

}

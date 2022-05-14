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

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class BookCommand implements Command {

    private static final BookService BOOK_SERVICE = new BookServiceImpl();

    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        BookDto bookDto = BOOK_SERVICE.getBookById(id);
        if (bookDto == null) {
            req.setAttribute("message", "no book with id: " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("book", bookDto);
        return "jsp/book/getBook.jsp";
    }

}

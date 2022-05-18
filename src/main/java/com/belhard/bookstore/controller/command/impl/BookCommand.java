package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookDto;
import com.belhard.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("book")
public class BookCommand implements Command {

    private BookService bookService;

    public BookCommand() {
    }

    @Autowired
    public void setBookService(BookService bookService) {
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

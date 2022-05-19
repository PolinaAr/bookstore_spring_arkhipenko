package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookDto;
import com.belhard.bookstore.service.BookService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("book")
public class BookCommand implements Command {

    private final BookService bookService;

    public BookCommand(BookService bookService) {
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

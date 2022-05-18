package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookDto;
import com.belhard.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("books")
public class BooksCommand implements Command {

    private BookService bookService;

    public BooksCommand() {
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public String execute(HttpServletRequest req) {
        List<BookDto> bookDtos = bookService.getAllBooks();
        req.setAttribute("books", bookDtos);
        return "jsp/book/getAllBooks.jsp";
    }
}

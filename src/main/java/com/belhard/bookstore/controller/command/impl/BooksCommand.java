package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookDto;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class BooksCommand implements Command {

    private BookService bookService = new BookServiceImpl();

    public String execute(HttpServletRequest req) {
        List<BookDto> bookDtos = bookService.getAllBooks();
        req.setAttribute("books", bookDtos);
        return "jsp/book/getAllBooks.jsp";
    }
}

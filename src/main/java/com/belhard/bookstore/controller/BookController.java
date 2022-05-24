package com.belhard.bookstore.controller;

import com.belhard.bookstore.exceptions.BookException;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.BookService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<BookDto> bookDtos = bookService.getAllBooks();
        model.addAttribute("books", bookDtos);
        return "getAllBooks";
    }

    @GetMapping("/{id}")
    public String getBookById(Model model, @PathVariable Long id) {
        try {
            BookDto bookDto = bookService.getBookById(id);
            model.addAttribute("book", bookDto);
            return "getBook";
        } catch (BookException e) {
            return "error";
        }
    }

}

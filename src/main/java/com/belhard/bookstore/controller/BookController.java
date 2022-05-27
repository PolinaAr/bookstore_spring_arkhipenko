package com.belhard.bookstore.controller;

import com.belhard.bookstore.exceptions.BookException;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
        return "books";
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

    @GetMapping("/create")
    public String createForm() {
        return "createBook";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBook (Model model, @RequestParam Map<String, Object> params) {
        try {
            BookDto bookDto = new BookDto();
            bookDto.setIsbn(params.get("isbn").toString());
            bookDto.setTitle(params.get("title").toString());
            bookDto.setAuthor(params.get("author").toString());
            bookDto.setPages(Integer.parseInt(params.get("pages").toString()));
            bookDto.setCover(BookDto.Cover.valueOf(params.get("cover").toString().toUpperCase()));
            bookDto.setPrice(BigDecimal.valueOf(Double.parseDouble(params.get("price").toString())));
            BookDto created = bookService.createBook(bookDto);
            model.addAttribute("book", created);
            return "getBook";
        } catch (BookException e) {
            return "error";
        }
    }

}

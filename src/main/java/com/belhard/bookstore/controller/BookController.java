package com.belhard.bookstore.controller;

import com.belhard.bookstore.controller.util.ParamReader;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private ParamReader paramReader;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setParamReader(ParamReader paramReader) {
        this.paramReader = paramReader;
    }

    @GetMapping
    public String getAllBooks(Model model, @RequestParam Map<String, Object> params) {
        Pageable pageable = paramReader.getPageable(params);
        List<BookDto> bookDtos = bookService.getAllBooks(pageable);
        model.addAttribute("books", bookDtos);
        return "book/books";
    }

    @GetMapping("/{id}")
    public String getBookById(Model model, @PathVariable Long id) {
        BookDto bookDto = bookService.getBookById(id);
        model.addAttribute("book", bookDto);
        return "book/getBook";
    }

    @GetMapping("/create")
    public String createForm() {
        return "book/createBook";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBook(Model model, @RequestParam Map<String, Object> params) {
        BookDto created = bookService.saveBook(setBookDto(params));
        model.addAttribute("book", created);
        return "book/getBook";
    }

    private BookDto setBookDto(Map<String, Object> params) {
        BookDto bookDto = new BookDto();
        bookDto.setIsbn(params.get("isbn").toString());
        bookDto.setTitle(params.get("title").toString());
        bookDto.setAuthor(params.get("author").toString());
        bookDto.setPages(Integer.parseInt(params.get("pages").toString()));
        bookDto.setCover(BookDto.Cover.valueOf(params.get("cover").toString().toUpperCase()));
        bookDto.setPrice(BigDecimal.valueOf(Double.parseDouble(params.get("price").toString())));
        return bookDto;
    }

    @GetMapping("/edit/{id}")
    public String updateForm(Model model, @PathVariable Long id) {
        BookDto bookDto = bookService.getBookById(id);
        model.addAttribute("book", bookDto);
        return "book/updateBook";
    }

    @PostMapping("/{id}")
    public String updateBook(Model model, @RequestParam Map<String, Object> params, @PathVariable Long id) {
        BookDto bookDto = setBookDto(params);
        bookDto.setId(id);
        BookDto updated = bookService.saveBook(bookDto);
        model.addAttribute("book", updated);
        return "book/getBook";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(Model model, @PathVariable Long id) {
        bookService.deleteBook(id);
        model.addAttribute("message", "The book with id " + id + " is successfully deleted.");
        return "delete";
    }
}

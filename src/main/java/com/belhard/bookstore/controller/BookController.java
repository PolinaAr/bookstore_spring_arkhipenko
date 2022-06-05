package com.belhard.bookstore.controller;

import com.belhard.bookstore.exceptions.BookException;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.util.ParamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private ParamReader paramReader;

    public BookController() {
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setParamReader(ParamReader paramReader) {
        this.paramReader = paramReader;
    }

    @GetMapping
    public String getAllBooks(Model model, @RequestParam Map<String, Object> params) {
        int page = paramReader.readPage(params);
        String direction = paramReader.readDirection(params);
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.valueOf(direction), "title");
        List<BookDto> bookDtos = bookService.getAllBooks(pageable);
        model.addAttribute("books", bookDtos);
        return "book/books";
    }

    @GetMapping("/{id}")
    public String getBookById(Model model, @PathVariable Long id) {
        try {
            BookDto bookDto = bookService.getBookById(id);
            model.addAttribute("book", bookDto);
            return "book/getBook";
        } catch (BookException e) {
            model.addAttribute("message", "There is no such book");
            return "error";
        }
    }

    @GetMapping("/create")
    public String createForm() {
        return "book/createBook";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBook(Model model, @RequestParam Map<String, Object> params) {
        try {
            BookDto created = bookService.saveBook(setBookDto(params));
            model.addAttribute("book", created);
            return "book/getBook";
        } catch (BookException e) {
            model.addAttribute("message", "The book is not created.");
            return "error";
        }
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
        try {
            BookDto bookDto = setBookDto(params);
            bookDto.setId(id);
            BookDto updated = bookService.saveBook(bookDto);
            model.addAttribute("book", updated);
            return "book/getBook";
        } catch (BookException e) {
            model.addAttribute("message", "The book is not updated");
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(Model model, @PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            model.addAttribute("message", "The book with id " + id + " is successfully deleted.");
            return "delete";
        } catch (BookException e) {
            model.addAttribute("message", "The book is not deleted");
            return "error";
        }
    }
}

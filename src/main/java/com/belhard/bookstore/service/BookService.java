package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.BookDto;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto getBookByIsbn(String isbn);

    List<BookDto> getBookByAuthor(String author);

    BookDto saveBook(BookDto bookDto);

    void deleteBook(Long id);

    Long countAllBooks();

    BigDecimal countPriceByAuthor(String author);
}

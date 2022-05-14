package com.belhard.bookstore.service;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto getBookByIsbn(String isbn);

    List<BookDto> getBookByAuthor(String author);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    void deleteBook(Long id);

    int countAllBooks();

    BigDecimal countPriceByAuthor(String author);
}

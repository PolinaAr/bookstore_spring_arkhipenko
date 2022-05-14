package com.belhard.bookstore.dao;

import java.util.List;

public interface BookDao {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book getBookByIsbn(String isbn);

    List<Book> getBookByAuthor(String author);

    Book createBook(Book book);

    Book updateBook(Book book);

    boolean deleteBook(Long id);

    int countAllBooks();
}

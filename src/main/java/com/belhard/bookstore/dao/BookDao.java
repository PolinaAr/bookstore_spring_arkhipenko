package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.entity.Book;

import java.util.List;

public interface BookDao extends AbstractDao<Book, Long> {

    Book getBookByIsbn(String isbn);

    List<Book> getBookByAuthor(String author);

    Long countAllBooks();
}

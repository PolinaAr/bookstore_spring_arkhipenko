package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.entity.Book;
import com.belhard.bookstore.dao.repository.BookRepository;
import com.belhard.bookstore.exceptions.BookException;
import com.belhard.bookstore.exceptions.UserException;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import net.bytebuddy.TypeCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.hql.internal.QueryExecutionRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private static final Logger logger = LogManager.getLogger(BookServiceImpl.class);

    public BookServiceImpl() {
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> getAllBooks(Pageable pageable) {
        logger.debug("Call method getAllBook");
        Page<Book> books = bookRepository.findBooksByDeletedFalse(pageable);
        List<BookDto> dtos = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = toDto(book);
            dtos.add(bookDto);
        }
        return dtos;
    }

    @Override
    public BookDto getBookById(Long id) {
        logger.debug("Call method getBookById");
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new UserException("There is no book with id " + id));
        return toDto(book);
    }

    @Override
    public BookDto getBookByIsbn(String isbn) {
        logger.debug("Call method getBookByIsbn");
        Book book = bookRepository.findBookByIsbn(isbn)
                .orElseThrow(() -> new UserException("There is no user with isbn " + isbn));
        return toDto(book);
    }

    @Override
    public List<BookDto> getBookByAuthor(String author) {
        logger.debug("Call method getBookByAuthor");
        Iterable<Book> books = bookRepository.findBooksByAuthor(author);
        List<BookDto> dtos = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = toDto(book);
            dtos.add(bookDto);
        }
        return dtos;
    }

    private BookDto toDto(Book entity) {
        BookDto bookDto = new BookDto();
        bookDto.setId(entity.getId());
        bookDto.setIsbn(entity.getIsbn());
        bookDto.setTitle(entity.getTitle());
        bookDto.setAuthor(entity.getAuthor());
        bookDto.setPages(entity.getPages());
        bookDto.setCover(BookDto.Cover.valueOf(entity.getCover().toString()));
        bookDto.setPrice(entity.getPrice());
        return bookDto;
    }

    @Override
    public BookDto saveBook(BookDto bookDto) {
        logger.debug("Call method createBook");
        try {
            Book createdBook = bookRepository.save(toBook(bookDto));
            BookDto createdBookDto = toDto(createdBook);
            return getBookById(createdBookDto.getId());
        } catch (IllegalArgumentException e) {
            throw new BookException("The book is not created");
        }
    }

    private Book toBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        if (bookDto.getIsbn().matches("\\d{3}-\\d-\\d{2}-\\d{6}-\\d")) {
            book.setIsbn(bookDto.getIsbn());
        } else {
            throw new BookException("Illegal input of isbn. Book was not created");
        }
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPages(bookDto.getPages());
        book.setCover(Book.Cover.valueOf(bookDto.getCover().toString()));
        book.setPrice(bookDto.getPrice());
        return book;
    }

    @Override
    public void deleteBook(Long id) {
        logger.debug("Call method deleteBook");
        try {
            bookRepository.softDelete(id);
        } catch (QueryExecutionRequestException e) {
            throw new BookException("The book with id " + id + " was not deleted");
        }
    }

    @Override
    public Long countAllBooks() {
        logger.debug("Call method countAllBooks");
        return bookRepository.countBookByDeletedFalse();
    }

    @Override
    public BigDecimal countPriceByAuthor(String author) {
        logger.debug("Call method countPriceByAuthor");
        List<BookDto> dtos = getBookByAuthor(author);
        if (dtos.isEmpty()) {
            throw new BookException("There is no books with such author: " + author);
        }
        BigDecimal allPrice = BigDecimal.ZERO;
        for (BookDto bookDto : dtos) {
            allPrice = allPrice.add(bookDto.getPrice());
        }
        return allPrice;
    }
}

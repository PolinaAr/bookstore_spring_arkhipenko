package com.belhard.bookstore.service;

import com.belhard.bookstore.dao.Book;
import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.exceptions.BookException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public BookServiceImpl() {
    }

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    private static final Logger logger = LogManager.getLogger(BookServiceImpl.class);

    @Override
    public List<BookDto> getAllBooks() {
        logger.debug("Call method getAllBook");
        List<Book> books = bookDao.getAllBooks();
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
        Book book = bookDao.getBookById(id);
        if (book == null) {
            throw new BookException("There is no book with id = " + id);
        }
        return toDto(book);
    }

    @Override
    public BookDto getBookByIsbn(String isbn) {
        logger.debug("Call method getBookByIsbn");
        Book book = bookDao.getBookByIsbn(isbn);
        if (book == null) {
            throw new BookException("There is no book with isbn = " + isbn);
        }
        return toDto(book);
    }

    @Override
    public List<BookDto> getBookByAuthor(String author) {
        logger.debug("Call method getBookByAuthor");
        List<Book> books = bookDao.getBookByAuthor(author);
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
    public BookDto createBook(BookDto bookDto) {
        logger.debug("Call method createBook");
        Book bookToCreate = toBook(bookDto);
        Book existing = bookDao.getBookByIsbn(bookToCreate.getIsbn());
        if (existing != null) {
            throw new BookException("This book is already exist.");
        }
        Book createdBook = bookDao.createBook(bookToCreate);
        if (createdBook == null) {
            throw new BookException("The book is not created");
        }
        BookDto createdBookDto = toDto(createdBook);
        return getBookById(createdBookDto.getId());
    }

    private Book toBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPages(bookDto.getPages());
        book.setCover(Book.Cover.valueOf(bookDto.getCover().toString()));
        book.setPrice(bookDto.getPrice());
        return book;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        logger.debug("Call method updateBook");
        Book bookToUpdate = toBook(bookDto);
        Book existing = bookDao.getBookByIsbn(bookToUpdate.getIsbn());
        if (existing != null && existing.getId() != bookDto.getId()) {
            throw new BookException("You can't update this book. Book with id " + bookDto.getId() + " already exist");
        }
        Book updatedBook = bookDao.updateBook(bookToUpdate);
        if (updatedBook == null) {
            throw new BookException("The book is not updated");
        }
        BookDto updatedBookDto = toDto(updatedBook);
        return getBookById(updatedBookDto.getId());
    }

    @Override
    public void deleteBook(Long id) {
        logger.debug("Call method deleteBook");
        if (!bookDao.deleteBook(id)) {
            throw new BookException("The book is not deleted");
        }
    }

    @Override
    public int countAllBooks() {
        logger.debug("Call method countAllBooks");
        return bookDao.countAllBooks();
    }

    @Override
    public BigDecimal countPriceByAuthor(String author) {
        logger.debug("Call method countPriceByAuthor");
        List<BookDto> dtos = getBookByAuthor(author);
        if (dtos.isEmpty()) {
            throw new BookException("There is no books with such author");
        }
        BigDecimal allPrice = BigDecimal.ZERO;
        for (BookDto bookDto : dtos) {
            allPrice = allPrice.add(bookDto.getPrice());
        }
        return allPrice;
    }
}

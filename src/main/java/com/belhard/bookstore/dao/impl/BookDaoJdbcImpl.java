package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.entity.Book;
import com.belhard.bookstore.service.exceptions.BookException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository("bookDao")
public class BookDaoJdbcImpl implements BookDao {

    private static final Logger logger = LogManager.getLogger(BookDaoJdbcImpl.class);

    public BookDaoJdbcImpl() {
    }

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public List<Book> getAllBooks() {
        List<Book> books = manager.createQuery("from Book where deleted=false", Book.class).getResultList();
        manager.clear();
        return books;
    }

    @Override
    @Transactional
    public Book getBookById(Long id) {
        try {
            Book book = manager.find(Book.class, id);
            manager.clear();
            return book;
        } catch (NoResultException e) {
            logger.info("The book was not received by id", e);
            return null;
        }
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        try {
            Book book = manager.createQuery("from Book where isbn = ?1 and deleted = false", Book.class)
                    .setParameter(1, isbn)
                    .getSingleResult();
            manager.clear();
            return book;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Book> getBookByAuthor(String author) {
        List<Book> books = manager.createQuery("from Book where author = ?1 and deleted = false", Book.class)
                .setParameter(1, author)
                .getResultList();
        manager.clear();
        return books;
    }


    @Override
    @Transactional
    public Book createBook(Book book) {
        try {
            manager.persist(book);
            manager.clear();
            return book;
        } catch (EntityExistsException | IllegalArgumentException e) {
            logger.error("The book has not been created", e);
            return null;
        }
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        try {
            manager.merge(book);
            return book;
        } catch (IllegalArgumentException e) {
            logger.error("The book has not been updated", e);
            return null;
        }
    }

    @Override
    @Transactional
    public boolean deleteBook(Long id) {
        try {
            Book book = manager.find(Book.class, id);
            book.setDeleted(true);
            manager.merge(book);
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("The book is not deleted.", e);
            throw new BookException("The book is not deleted");
        }
    }

    @Override
    public Long countAllBooks() {
        try {
            Long result = (Long) manager.createQuery("SELECT COUNT(*) FROM Book WHERE deleted = false").getSingleResult();
            manager.clear();
            return result;
        } catch (IllegalArgumentException e) {
            logger.error("The books are not counted", e);
            throw new BookException("The books are not counted");
        }
    }

}

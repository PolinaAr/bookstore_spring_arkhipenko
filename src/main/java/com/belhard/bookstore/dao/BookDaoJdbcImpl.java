package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.RowMappers.BookRowMapper;
import com.belhard.bookstore.exceptions.BookException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("bookDao")
public class BookDaoJdbcImpl implements BookDao {

    private final NamedParameterJdbcTemplate template;
    private final BookRowMapper rowMapper;

    @Autowired
    public BookDaoJdbcImpl(NamedParameterJdbcTemplate template, BookRowMapper rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }

    private static final Logger logger = LogManager.getLogger(BookDaoJdbcImpl.class);
    public static final String GET_ALL = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b " +
            "JOIN covers c ON b.cover_id = c.id WHERE deleted = false";
    public static final String GET_BOOK_BY_ID = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b " +
            "JOIN covers c ON b.cover_id = c.id WHERE b.id = :id AND b.deleted = false";
    public static final String GET_BOOK_BY_ISBN = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b " +
            "JOIN covers c ON b.cover_id = c.id WHERE isbn = :isbn AND deleted = false";
    public static final String GET_BOOK_BY_AUTHOR = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b " +
            "JOIN covers c ON b.cover_id = c.id WHERE author = :author AND deleted = false";
    public static final String DELETE = "UPDATE books SET deleted = true WHERE id = :id";
    public static final String INSERT = "INSERT INTO books (isbn, title, author, pages, cover_id , price) " +
            "VALUES (:isbn, :title, :author, :pages, (SELECT id FROM covers WHERE name = :coverName), :price);";
    public static final String UPDATE = "UPDATE books SET isbn= :isbn, title = :title, author = :author, " +
            "pages = :pages, cover_id = (SELECT id FROM covers WHERE name = :coverName), price = :price where id= :id";
    public static final String COUNT_ALL_BOOKS = "SELECT COUNT(*) AS count FROM books WHERE deleted = false";

    @Override
    public List<Book> getAllBooks() {
        return template.query(GET_ALL, rowMapper);
    }

    @Override
    public Book getBookById(Long id) {
        try {
            return template.queryForObject(GET_BOOK_BY_ID, Map.of("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The book was not received by id.", e);
            return null;
        }
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        try {
            return template.queryForObject(GET_BOOK_BY_ISBN, Map.of("isbn", isbn), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The book was not received by isbn.", e);
            return null;
        }
    }

    @Override
    public List<Book> getBookByAuthor(String author) {
        return template.query(GET_BOOK_BY_AUTHOR, Map.of("author", author), rowMapper);
    }


    @Override
    public Book createBook(Book book) {
        try {
            SqlParameterSource source = new MapSqlParameterSource(getMap(book));
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = template.update(INSERT, source, keyHolder, new String[]{"id"});
            if (result != 1) {
                logger.error("Error of execute update when creating a book");
                return null;
            }
            Long id = Optional.ofNullable(keyHolder.getKey()).map(Number::longValue).get();
            if (id != 0) {
                return getBookById(id);
            } else {
                logger.error("The book didn't created");
                return null;
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("The book has not been created", e);
            return null;
        }
    }

    private Map<String, Object> getMap(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", book.getIsbn());
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put("pages", book.getPages());
        params.put("coverName", book.getCover().toString().toUpperCase());
        params.put("price", book.getPrice());
        return params;
    }

    @Override
    public Book updateBook(Book book) {
        try {
            Map<String, Object> params = getMap(book);
            params.put("id", book.getId());
            int result = template.update(UPDATE, params);
            if (result != 1) {
                logger.error("Error of execute update when updating a book");
                return null;
            }
            return getBookById(book.getId());
        } catch (EmptyResultDataAccessException e) {
            logger.error("The book has not been updated", e);
            return null;
        }
    }

    @Override
    public boolean deleteBook(Long id) {
        try {
            int result = template.update(DELETE, Map.of("id", id));
            return result == 1;
        } catch (EmptyResultDataAccessException e) {
            logger.error("The book is not deleted.", e);
            throw new BookException("The book is not deleted");
        }
    }

    @Override
    public int countAllBooks() {
        try {
            return template.query(DELETE, (rs, rowNum) -> rs.getInt("count")).get(0);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The books are not counted", e);
            throw new BookException("The books are not counted");
        }
    }

}

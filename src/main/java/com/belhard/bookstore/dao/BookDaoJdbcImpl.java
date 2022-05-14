package com.belhard.bookstore.dao;

import com.belhard.bookstore.util.DbConfigurator;
import com.belhard.bookstore.exceptions.BookException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJdbcImpl implements BookDao {

    private static final Logger logger = LogManager.getLogger(BookDaoJdbcImpl.class);
    public static final String GET_ALL = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b " +
            "JOIN covers c ON b.cover_id = c.id WHERE deleted = false";
    public static final String GET_BOOK_BY_ID = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b " +
            "JOIN covers c ON b.cover_id = c.id WHERE b.id = ? AND b.deleted = false";
    public static final String GET_BOOK_BY_ISBN = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b " +
            "JOIN covers c ON b.cover_id = c.id WHERE isbn = ? AND deleted = false";
    public static final String GET_BOOK_BY_AUTHOR = "SELECT b.id, b.isbn, b.title, b.author, b.pages, c.name AS cover, b.price FROM books b " +
            "JOIN covers c ON b.cover_id = c.id WHERE author = ? AND deleted = false";
    public static final String DELETE = "UPDATE books SET deleted = true WHERE id = ?";
    public static final String INSERT = "INSERT INTO books (isbn, title, author, pages, cover_id , price) " +
            "VALUES (?, ?, ?, ?, (SELECT id FROM covers WHERE name = ?), ?);";
    public static final String UPDATE = "UPDATE books SET isbn= ?, title = ?, author = ?, " +
            "pages = ?, cover_id = (SELECT id FROM covers WHERE name = ?), price = ? where id= ?";
    public static final String COUNT_ALL_BOOKS = "SELECT COUNT(*) AS count FROM books WHERE deleted = false";

    @Override
    public List<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                books.add(processResultGet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("The list of books was not received.", e);
        }
        return books;
    }

    private Book processResultGet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPages(resultSet.getInt("pages"));
        book.setCover(Book.Cover.valueOf(resultSet.getString("cover")));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }

    @Override
    public Book getBookById(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(GET_BOOK_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return processResultGet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("The book was not received by id.", e);
        }
        return null;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BOOK_BY_ISBN);
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return processResultGet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("The book was not received by isbn.", e);
        }
        return null;
    }

    @Override
    public List<Book> getBookByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BOOK_BY_AUTHOR);
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(processResultGet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("The book was not received by author", e);
        }
        return books;
    }


    @Override
    public Book createBook(Book book) {
        try {
            PreparedStatement statement =
                    DbConfigurator.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            prepareStatement(book, statement);
            int result = statement.executeUpdate();
            if (result != 1) {
                throw new BookException("Error of execute update when creating a book");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return getBookById(generatedKeys.getLong("id"));
            }
        } catch (SQLException e) {
            logger.error("The book has not been created", e);
        }
        return null;
    }

    private void prepareStatement(Book book, PreparedStatement statement) throws SQLException {
        statement.setString(1, book.getIsbn());
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getAuthor());
        statement.setInt(4, book.getPages());
        statement.setString(5, book.getCover().toString());
        statement.setBigDecimal(6, book.getPrice());
    }

    @Override
    public Book updateBook(Book book) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(UPDATE);
            prepareStatement(book, statement);
            statement.setLong(7, book.getId());
            int result = statement.executeUpdate();
            if (result != 1) {
                throw new BookException("Error of execute update when updating a book");
            }
            return getBookById(book.getId());
        } catch (SQLException e) {
            logger.error("The book has not been updated", e);
        }
        return null;
    }

    @Override
    public boolean deleteBook(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            logger.error("The book is not deleted.", e);
        }
        throw new BookException("The book is not deleted");
    }

    @Override
    public int countAllBooks() {
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL_BOOKS);
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("The books are not counted", e);
        }
        throw new BookException("The books are not counted");
    }

}

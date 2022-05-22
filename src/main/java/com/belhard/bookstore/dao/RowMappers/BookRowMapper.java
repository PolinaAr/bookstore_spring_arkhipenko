package com.belhard.bookstore.dao.RowMappers;

import com.belhard.bookstore.dao.entity.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setIsbn(rs.getString("isbn"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPages(rs.getInt("pages"));
        book.setCover(Book.Cover.valueOf(rs.getString("cover")));
        book.setPrice(rs.getBigDecimal("price"));
        return book;
    }
}

package com.belhard.bookstore.dao.repository;

import com.belhard.bookstore.dao.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Iterable<Book> findBooksByDeletedFalse();

    Optional<Book> findBookByIsbn(String isbn);

    Iterable<Book> findBooksByAuthor(String author);

    Long countBookByDeletedFalse();

    @Transactional
    @Modifying
    @Query("UPDATE Book SET deleted = true where id = :id")
    void softDelete(@Param("id") Long id);
}

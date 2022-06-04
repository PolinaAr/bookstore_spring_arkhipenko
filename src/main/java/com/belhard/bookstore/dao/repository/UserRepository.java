package com.belhard.bookstore.dao.repository;

import com.belhard.bookstore.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Iterable<User> findUsersByDeletedFalse();

    Optional<User> findUserByEmail(String email);

    Iterable<User> findUsersByLastName(String lastName);

    @Query("SELECT COUNT(*) FROM User where deleted = false")
    Long countUsers();

    @Transactional
    @Modifying
    @Query("UPDATE User SET deleted = true where id = :id")
    void softDelete(@Param("id") Long id);
}

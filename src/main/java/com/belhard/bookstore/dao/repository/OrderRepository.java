package com.belhard.bookstore.dao.repository;

import com.belhard.bookstore.dao.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Order SET status = 'canceled' where id = :id")
    void softDelete(@Param("id") Long id);
}

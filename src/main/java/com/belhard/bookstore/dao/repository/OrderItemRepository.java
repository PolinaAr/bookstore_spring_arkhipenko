package com.belhard.bookstore.dao.repository;

import com.belhard.bookstore.dao.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Iterable<OrderItem> findOrderItemByOrder_Id(Long id);
}

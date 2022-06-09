package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.entity.OrderItem;

import java.util.List;

public interface OrderItemDao extends AbstractDao<OrderItem, Long> {

    List<OrderItem> getByOrderId(Long orderId);
}

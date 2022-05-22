package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.entity.OrderItem;

import java.util.List;

public interface OrderItemDao {

    List<OrderItem> getAllOrderItem();

    OrderItem getById(Long id);

    List<OrderItem> getByOrderId(Long orderId);

    OrderItem createOrderItem(OrderItem orderItem);

    OrderItem updateOrderItem(OrderItem orderItem);

    boolean deleteOrderItem(Long id);
}

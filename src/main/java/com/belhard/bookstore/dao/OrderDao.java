package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.entity.Order;

import java.util.List;

public interface OrderDao {

    List<Order> getAllOrders();

    Order getOrderById(Long id);

    Order createOrder(Order order);

    Order updateOrder(Order order);

    boolean deleteOrder(Long id);
}

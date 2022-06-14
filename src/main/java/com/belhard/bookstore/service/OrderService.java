package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.OrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrders(Pageable pageable);

    OrderDto getById(Long id);

    List<OrderDto> getOrdersByUserId(Long id);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrder(OrderDto orderDto);

    void deleteOrder(Long id);
}

package com.belhard.bookstore.controller;

import com.belhard.bookstore.dao.entity.Order;
import com.belhard.bookstore.dao.repository.OrderItemRepository;
import com.belhard.bookstore.exceptions.OrderException;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;
    private BookService bookService;
@Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
@Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public OrderController() {
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String getOrderById(Model model, @PathVariable Long id) {
        try {
            OrderDto orderDto = orderService.getById(id);
            model.addAttribute("order", orderDto);
            List<OrderItemDto> items = orderDto.getItems();
            model.addAttribute("items", items);
            return "order/getOrder";
        } catch (OrderException e) {
            model.addAttribute("message", "This order is not found");
            return "error";
        }
    }
}

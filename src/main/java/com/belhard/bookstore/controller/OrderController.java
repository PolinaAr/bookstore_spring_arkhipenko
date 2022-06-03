package com.belhard.bookstore.controller;

import com.belhard.bookstore.exceptions.OrderException;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

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
        return "orders";
    }

    @GetMapping("/{id}")
    public String getOrderById(Model model, @PathVariable Long id) {
        try {
            OrderDto orderDto = orderService.getById(id);
            model.addAttribute("order", orderDto);
            List<OrderItemDto> items = orderDto.getItems();
            model.addAttribute("items", items);
            return "getOrder";
        } catch (OrderException e) {
            model.addAttribute("message", "This order ia not found");
            return "error";
        }
    }
}

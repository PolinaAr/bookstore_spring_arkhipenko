package com.belhard.bookstore.controller;

import com.belhard.bookstore.exceptions.OrderException;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.util.ParamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private ParamReader paramReader;

    public OrderController() {
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setParamReader(ParamReader paramReader) {
        this.paramReader = paramReader;
    }

    @GetMapping
    public String getAllOrders(Model model, @RequestParam Map<String, Object> params) {
        Pageable pageable = paramReader.getPageable(params);
        List<OrderDto> orders = orderService.getAllOrders(pageable);
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

    @GetMapping("/user/{id}")
    public String getOrderByUserId(Model model, @PathVariable Long id) {
        List<OrderDto> orders = orderService.getOrdersByUserId(id);
        model.addAttribute("orders", orders);
        for (OrderDto order : orders) {
            List<OrderItemDto> items = order.getItems();
            model.addAttribute("items", items);
        }
        return "order/ordersByUser";
    }

}

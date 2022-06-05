package com.belhard.bookstore.controller;

import com.belhard.bookstore.exceptions.OrderException;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.util.ParamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component
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
        int page = paramReader.readPage(params);
        String direction = paramReader.readDirection(params);
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.valueOf(direction), "id");
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
}

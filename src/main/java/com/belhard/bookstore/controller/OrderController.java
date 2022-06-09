package com.belhard.bookstore.controller;

import com.belhard.bookstore.exceptions.OrderException;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.util.ReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ReaderUtil readerUtil;

    public OrderController() {
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setReaderUtil(ReaderUtil readerUtil) {
        this.readerUtil = readerUtil;
    }

    @GetMapping
    public String getAllOrders(Model model, @RequestParam Map<String, Object> params) {
        int page = readerUtil.readPage(params);
        int items = readerUtil.readQuantityOfItems(params);
        String sortColumn = readerUtil.readSortColumn(params);
        String direction = readerUtil.readDirection(params);
        List<OrderDto> orders = orderService.getAllOrders(page, items, sortColumn, direction);
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

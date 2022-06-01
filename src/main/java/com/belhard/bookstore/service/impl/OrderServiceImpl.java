package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.entity.Order;
import com.belhard.bookstore.dao.entity.OrderItem;
import com.belhard.bookstore.exceptions.OrderException;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private OrderDao orderDao;
    private BookDao bookDao;
    private UserDao userDao;
    private OrderItemDao orderItemDao;
    private UserService userService;
    private BookService bookService;

    public OrderServiceImpl() {
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Autowired
    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderDao.getAllOrders();
        List<OrderDto> dtos = new ArrayList<>();
        for (Order order : orders){
            OrderDto orderDto = toDto(order);
            dtos.add(orderDto);
        }
        return dtos;
    }

    @Override
    public OrderDto getById(Long id) {
        Order order = orderDao.getOrderById(id);
        if (order == null) {
            logger.error("The order was not created");
            throw new OrderException("The order was not created");
        }
        return toDto(order);
    }

    private OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserDto(userService.getUserById(order.getUser().getId()));
        orderDto.setTotalCost(order.getTotalCost());
        orderDto.setTimestamp(order.getTimestamp());
        orderDto.setStatus(Order.Status.valueOf(order.getStatus().toString().toUpperCase()));
        List<OrderItem> orderItems = orderItemDao.getByOrderId(order.getId());
        List<OrderItemDto> dtos = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setId(orderItem.getId());
            orderItemDto.setBookDto(bookService.getBookById(orderItem.getBook().getId()));
            orderItemDto.setQuantity(orderItem.getQuantity());
            orderItemDto.setPrice(orderItem.getPrice());
            dtos.add(orderItemDto);
        }
        orderDto.setItems(dtos);
        return orderDto;
    }


    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order toCreate = toEntity(orderDto);
        Order created = orderDao.createOrder(toCreate);
        if (created == null) {
            logger.error("The order was not created");
            throw new OrderException("The order was not created");
        }
        List<OrderItemDto> items = orderDto.getItems();
        for (OrderItemDto itemDto : items) {
            itemDto.setOrderId(created.getId());
        }
        createOrderItem(items, toDto(created));
        return getById(created.getId());
    }

    private void createOrderItem(List<OrderItemDto> items, OrderDto orderDto) {
        for (OrderItemDto itemDto : items) {
            itemDto.setOrderId(orderDto.getId());
            OrderItem createdOrderItem = orderItemDao.createOrderItem(itemToEntity(itemDto));
            if (createdOrderItem == null) {
                logger.error("The order item was not created");
                throw new OrderException("The order item was not created");
            }
        }
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        Order entity = toEntity(orderDto);
        Order updatedOrder = orderDao.updateOrder(entity);
        if (updatedOrder == null) {
            throw new OrderException("The order was not updated");
        }

        List<OrderItem> items = orderItemDao.getByOrderId(orderDto.getId());
        items.forEach(i -> orderItemDao.deleteOrderItem(i.getId()));

        createOrderItem(orderDto.getItems(), orderDto);
        return getById(orderDto.getId());
    }

    private OrderItem itemToEntity(OrderItemDto itemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orderDao.getOrderById(itemDto.getOrderId()));
        orderItem.setPrice(itemDto.getPrice());
        orderItem.setQuantity(itemDto.getQuantity());
        orderItem.setBook(bookDao.getBookById(itemDto.getBookDto().getId()));
        return orderItem;
    }

    private Order toEntity(OrderDto orderDto) {
        Order entity = new Order();
        entity.setId(orderDto.getId());
        entity.setUser(userDao.getUserById(orderDto.getUserDto().getId()));
        BigDecimal totalCost = calculateTotalCost(orderDto);
        entity.setTotalCost(totalCost);
        entity.setStatus(Order.Status.valueOf(orderDto.getStatus().toString().toUpperCase()));
        entity.setTimestamp(orderDto.getTimestamp());
        return entity;
    }

    private BigDecimal calculateTotalCost(OrderDto orderDto) {
        List<OrderItemDto> items = orderDto.getItems();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            BigDecimal itemCost = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderDao.deleteOrder(id)){
            throw new OrderException("The book was not deleted.");
        };
    }


}

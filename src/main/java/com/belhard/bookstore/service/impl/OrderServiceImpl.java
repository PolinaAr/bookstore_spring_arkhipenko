package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.entity.Order;
import com.belhard.bookstore.dao.entity.OrderItem;
import com.belhard.bookstore.dao.repository.BookRepository;
import com.belhard.bookstore.dao.repository.OrderItemRepository;
import com.belhard.bookstore.dao.repository.OrderRepository;
import com.belhard.bookstore.dao.repository.UserRepository;
import com.belhard.bookstore.exceptions.OrderException;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.hql.internal.QueryExecutionRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private UserService userService;
    private BookService bookService;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    public OrderServiceImpl() {
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
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        Iterable<Order> orders = orderRepository.findAll();
        List<OrderDto> dtos = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = toDto(order);
            dtos.add(orderDto);
        }
        return dtos;
    }

    @Override
    public OrderDto getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("There is no order with id " + id));
        return toDto(order);
    }

    private OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserDto(userService.getUserById(order.getUser().getId()));
        orderDto.setTotalCost(order.getTotalCost());
        orderDto.setTimestamp(order.getTimestamp());
        orderDto.setStatus(Order.Status.valueOf(order.getStatus().toString().toUpperCase()));
        Iterable<OrderItem> orderItems = orderItemRepository.findOrderItemByOrder_Id(order.getId());
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
        Order order = toEntity(orderDto);
        Order created = orderRepository.save(order);

        saveOrderItemDto(orderDto, created);
        return getById(created.getId());
    }

    private void saveOrderItemDto(OrderDto orderDto, Order created) {
        List<OrderItemDto> itemsDto = orderDto.getItems();
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDto itemDto : itemsDto) {
            itemDto.setOrderId(created.getId());
            items.add(itemToEntity(itemDto));
        }
        orderItemRepository.saveAll(items);
    }

    @Override
    @Transactional
    public OrderDto updateOrder(OrderDto orderDto) {
        Order entity = toEntity(orderDto);
        Order updated = orderRepository.save(entity);

        Iterable<OrderItem> items =orderItemRepository.findOrderItemByOrder_Id(orderDto.getId());
        orderItemRepository.deleteAll(items);

        saveOrderItemDto(orderDto, updated);
        return getById(orderDto.getId());
    }

    private OrderItem itemToEntity(OrderItemDto itemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orderRepository.findById(itemDto.getOrderId())
                .orElseThrow(() -> new OrderException("Error of setting order in orderItem")));
        orderItem.setPrice(bookRepository.findById(itemDto.getBookDto().getId())
                .orElseThrow(() -> new OrderException("Error of setting price in orderItem"))
                .getPrice());
        orderItem.setQuantity(itemDto.getQuantity());
        orderItem.setBook(bookRepository.findById(itemDto.getBookDto().getId())
                .orElseThrow(() -> new OrderException("Error of setting book in orderItem")));
        return orderItem;
    }

    private Order toEntity(OrderDto orderDto) {
        Order entity = new Order();
        entity.setId(orderDto.getId());
        entity.setTimestamp(LocalDateTime.now());
        entity.setUser(userRepository.findById(orderDto.getUserDto().getId())
                .orElseThrow(() -> new OrderException("Error of setting user is order")));
        BigDecimal totalCost = calculateTotalCost(orderDto);
        entity.setTotalCost(totalCost);
        entity.setStatus(Order.Status.valueOf(orderDto.getStatus().toString().toUpperCase()));
        return entity;
    }

    private BigDecimal calculateTotalCost(OrderDto orderDto) {
        List<OrderItemDto> items = orderDto.getItems();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            BigDecimal itemCost = item.getBookDto().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }

    @Override
    public void deleteOrder(Long id) {
        try {
            orderRepository.softDelete(id);
        } catch (QueryExecutionRequestException e) {
            throw new OrderException("The order with id " + id + " was not created");
        }
    }


}

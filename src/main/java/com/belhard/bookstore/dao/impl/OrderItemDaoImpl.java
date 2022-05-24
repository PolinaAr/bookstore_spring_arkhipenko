package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.RowMappers.OrderItemRowMapper;
import com.belhard.bookstore.dao.entity.OrderItem;
import com.belhard.bookstore.exceptions.OrderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    public static final String GET_ALL = "SELECT id, order_id, book_id, quantity, price FROM orderItem";
    public static final String GET_BY_ID = "SELECT id, order_id, book_id, quantity, price FROM orderItem " +
            "WHERE id = :id";
    public static final String GET_BY_ORDER_ID = "SELECT id, order_id, book_id, quantity, price FROM orderItem " +
            "WHERE order_id = :order_id";
    public static final String INSERT = "INSERT INTO orderItem (order_id, book_id, quantity, price) " +
            "VALUES (:order_id, :book_id, :quantity, :price)";
    public static final String UPDATE = "UPDATE orderItem SET order_id= :order_d, book_id = :book_id," +
            "quantity = :quantity, price = :price WHERE id= :id";
    public static final String DELETE = "DELETE FROM orderItem WHERE id = :id";

    private final NamedParameterJdbcTemplate template;
    private final OrderItemRowMapper rowMapper;
    private static final Logger logger = LogManager.getLogger(OrderItemDaoImpl.class);

    @Autowired
    public OrderItemDaoImpl(NamedParameterJdbcTemplate template, OrderItemRowMapper rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<OrderItem> getAllOrderItem() {
        return template.query(GET_ALL, rowMapper);
    }

    @Override
    public OrderItem getById(Long id) {
        try {
            return template.queryForObject(GET_BY_ID, Map.of("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The orderItem was not received by orderItem id", e);
            return null;
        }
    }

    @Override
    public List<OrderItem> getByOrderId(Long orderId) {
        return template.query(GET_BY_ORDER_ID, Map.of("order_id", orderId), rowMapper);
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        try {
            SqlParameterSource source = new MapSqlParameterSource(getMap(orderItem));
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = template.update(INSERT, source, keyHolder, new String[]{"id"});
            if (result != 1) {
                logger.error("The order item was not created");
                return null;
            }
            Long id = Optional.ofNullable(keyHolder.getKey().longValue()).get();
            if (id == 0) {
                logger.error("The order item was not created");
                return null;
            }
            return getById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The order item was not created", e);
            return null;
        }
    }

    private Map<String, Object> getMap(OrderItem orderItem) {
        Map<String, Object> params = new HashMap<>();
        params.put("order_id", orderItem.getOrderId());
        params.put("book_id", orderItem.getBookId());
        params.put("quantity", orderItem.getQuantity());
        params.put("price", orderItem.getPrice());
        return params;
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        try {
            Map<String, Object> params = getMap(orderItem);
            params.put("id", orderItem.getOrderId());
            int result = template.update(UPDATE, params);
            if (result != 1) {
                logger.error("The order item didn't up");
                return null;
            }
            return getById(orderItem.getId());
        } catch (EmptyResultDataAccessException e) {
            logger.error("The order item was not created", e);
            return null;
        }
    }

    @Override
    public boolean deleteOrderItem(Long id) {
        try {
            return template.update(DELETE, Map.of("id", id)) == 1;
        } catch (EmptyResultDataAccessException e) {
            logger.error("The order item was not deleted", e);
            throw new OrderException("The order item was not deleted");
        }
    }
}

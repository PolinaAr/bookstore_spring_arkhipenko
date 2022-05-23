package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.RowMappers.OrderRowMapper;
import com.belhard.bookstore.dao.entity.Order;
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
public class OrderDaoImpl implements OrderDao {

    public static final String GET_ALL = "SELECT o.id, o.user_id, o.total_cost, o.timestamp, s.name AS status FROM orders o" +
            "JOIN status s ON o.status_id = s.id";
    public static final String GET_BY_ID = "SELECT o.id, o.user_id, o.total_cost, o.timestamp, s.name AS status FROM orders o\n" +
            "JOIN status s ON o.status_id = s.id WHERE id = :id";
    public static final String INSERT = "INSERT INTO orders (user_id, total_cost, status_id) " +
            "VALUES (:userId, :total_cost, (SELECT id FROM status WHERE name = :status))";
    public static final String UPDATE = "UPDATE orders SET user_id= :userId, total_cost = :total_cost, " +
            "timestamp = :timestamp, status_id = (SELECT id FROM status WHERE name = :status) WHERE id= :id";
    public static final String DELETE = "UPDATE orders SET status_id = (SELECT id FROM status WHERE name = 'canceled' " +
            "WHERE id = :id";

    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    private final NamedParameterJdbcTemplate template;
    private final OrderRowMapper rowMapper;

    @Autowired
    public OrderDaoImpl(NamedParameterJdbcTemplate template, OrderRowMapper rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Order> getAllOrders() {
        return template.query(GET_ALL, rowMapper);
    }

    @Override
    public Order getOrderById(Long id) {
        try {
            return template.queryForObject(GET_BY_ID, Map.of("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The order was not received by id", e);
            return null;
        }
    }

    @Override
    public Order createOrder(Order order) {
        try {
            SqlParameterSource source = new MapSqlParameterSource(getMap(order));
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = template.update(INSERT, source, keyHolder, new String[]{"id"});
            if (result != 1) {
                logger.error("The order didn't created");
                return null;
            }
            Long id = Optional.ofNullable(keyHolder.getKey().longValue()).get();
            if (id == 0) {
                logger.error("The order didn't created");
                return null;
            }
            return getOrderById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The order didn't created", e);
            return null;
        }
    }

    private Map<String, Object> getMap(Order order) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", order.getUserId());
        params.put("total_cost", order.getTotalCost());
        params.put("status", order.getStatus().toString().toLowerCase());
        return params;
    }

    @Override
    public Order updateOrder(Order order) {
        try {
            Map<String, Object> params = getMap(order);
            params.put("id", order.getId());
            params.put("timestamp", order.getTimestamp());
            int result = template.update(UPDATE, params);
            if (result != 1) {
                logger.error("The order didn't update");
                return null;
            }
            return getOrderById(order.getId());
        } catch (EmptyResultDataAccessException e) {
            logger.error("The order didn't update", e);
            return null;
        }
    }

    @Override
    public boolean deleteOrder(Long id) {
        try {
            int result = template.update(DELETE, Map.of("id", id));
            return result == 1;
        } catch (EmptyResultDataAccessException e) {
            logger.error("The order didn't deleted", e);
            throw new OrderException("The order didn't delete");
        }
    }
}

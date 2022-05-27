package com.belhard.bookstore.dao.RowMappers;

import com.belhard.bookstore.dao.entity.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setUserId(rs.getLong("user_id"));
        order.setTotalCost(rs.getBigDecimal("total_cost"));
        order.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        order.setStatus(Order.Status.valueOf(rs.getString("status").toUpperCase()));
        return order;
    }
}

package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.dao.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderDto {

    private Long id;
    private UserDto userDto;
    private BigDecimal totalCost;
    private LocalDateTime timestamp;
    private Order.Status status;
    private List<OrderItemDto> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Order.Status getStatus() {
        return status;
    }

    public void setStatus(Order.Status status) {
        this.status = status;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(userDto, orderDto.userDto) && Objects.equals(totalCost, orderDto.totalCost) && Objects.equals(timestamp, orderDto.timestamp) && status == orderDto.status && Objects.equals(items, orderDto.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCost, timestamp, status, items);
    }

    @Override
    public String toString() {
        return "\nOrderDto{" +
                "id=" + id +
                ", userDto=" + userDto +
                ", totalCost=" + totalCost +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", items=" + items +
                '}';
    }
}

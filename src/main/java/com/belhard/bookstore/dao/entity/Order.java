package com.belhard.bookstore.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private LocalDateTime timestamp;
    private Status status;

    public enum Status {
        CANCELED, COMPLETED, AWAITING_PAYMENT, IN_WAY, PROCESSING
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(userId, order.userId)
                && Objects.equals(totalPrice, order.totalPrice)
                && Objects.equals(timestamp, order.timestamp)
                && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, totalPrice, timestamp, status);
    }

    @Override
    public String toString() {
        return "\nOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }
}

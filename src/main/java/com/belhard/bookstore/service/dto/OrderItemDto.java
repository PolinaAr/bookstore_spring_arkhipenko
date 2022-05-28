package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.entity.Book;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItemDto {

    private Long id;
    private Long orderId;
    private BookDto bookDto;
    private int quantity;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BookDto getBookDto() {
        return bookDto;
    }

    public void setBookDto(BookDto bookDto) {
        this.bookDto = bookDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return quantity == that.quantity && Objects.equals(orderId, that.orderId) && Objects.equals(bookDto, that.bookDto) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, bookDto, quantity, price);
    }

    @Override
    public String toString() {
        return "\nOrderItemDto{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", bookDto=" + bookDto +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}

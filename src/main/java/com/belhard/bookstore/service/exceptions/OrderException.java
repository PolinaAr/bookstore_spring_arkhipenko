package com.belhard.bookstore.service.exceptions;

public class OrderException extends RuntimeException {

    public OrderException(String message) {
        super(message);
    }
}

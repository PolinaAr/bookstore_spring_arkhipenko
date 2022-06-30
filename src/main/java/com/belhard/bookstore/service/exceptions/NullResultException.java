package com.belhard.bookstore.service.exceptions;

public class NullResultException extends RuntimeException {

    public NullResultException(String message) {
        super(message);
    }
}

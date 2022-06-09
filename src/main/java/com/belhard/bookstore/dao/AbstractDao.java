package com.belhard.bookstore.dao;

import java.util.List;

public interface AbstractDao<T, K> {

    List<T> findAll(int page, int items, String sortColumn, String direction);

    T find(K key);

    T create(T entity);

    T update(T entity);

    boolean delete(K key);
}

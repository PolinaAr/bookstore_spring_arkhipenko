package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.entity.OrderItem;
import com.belhard.bookstore.exceptions.OrderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    private static final Logger logger = LogManager.getLogger(OrderItemDaoImpl.class);

    public OrderItemDaoImpl() {
    }

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<OrderItem> findAll(int page, int item, String sortColumn, String direction) {
        List<OrderItem> items = manager.createQuery("from OrderItem order by ?1 ?2", OrderItem.class)
                .setFirstResult(page)
                .setMaxResults(item)
                .setParameter(1, sortColumn)
                .setParameter(2, direction)
                .getResultList();
        manager.clear();
        return items;
    }

    @Override
    public OrderItem find(Long id) {
        try {
            OrderItem item = manager.find(OrderItem.class, id);
            manager.clear();
            return item;
        } catch (NoResultException e) {
            logger.info("The orderItem was not received by orderItem id", e);
            return null;
        }
    }

    @Override
    public List<OrderItem> getByOrderId(Long orderId) {
        List<OrderItem> items = manager.createQuery("from OrderItem where order_id = ?1", OrderItem.class)
                .setParameter(1, orderId)
                .getResultList();
        manager.clear();
        return items;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        try {
            manager.persist(orderItem);
            manager.clear();
            return orderItem;
        } catch (EntityExistsException | IllegalArgumentException e) {
            logger.error("The order item was not created", e);
            return null;
        }
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        try {
            manager.merge(orderItem);
            manager.clear();
            return orderItem;
        } catch (IllegalArgumentException e) {
            logger.error("The order item was not created", e);
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            manager.remove(getByOrderId(id));
            manager.clear();
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("The order item was not deleted", e);
            throw new OrderException("The order item was not deleted");
        }
    }
}

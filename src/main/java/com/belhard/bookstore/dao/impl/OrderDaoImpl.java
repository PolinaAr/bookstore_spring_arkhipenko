package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.entity.Order;
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
public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    public OrderDaoImpl() {
    }

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Order> findAll(int page, int items, String sortColumn, String direction) {
        List<Order> orders = manager.createQuery("from Order order by ?1 ?2", Order.class)
                .setFirstResult(page)
                .setMaxResults(items)
                .setParameter(1, sortColumn)
                .setParameter(2, direction)
                .getResultList();
        manager.clear();
        return orders;
    }

    @Override
    public Order find(Long id) {
        try {
            Order order = manager.find(Order.class, id);
            manager.clear();
            return order;
        } catch (NoResultException e) {
            logger.info("The order was not received by id", e);
            return null;
        }
    }

    @Override
    public Order create(Order order) {
        try {
            manager.persist(order);
            manager.clear();
            return order;
        } catch (EntityExistsException | IllegalArgumentException e) {
            logger.error("The order didn't created", e);
            return null;
        }
    }

    @Override
    public Order update(Order order) {
        try {
            manager.merge(order);
            manager.clear();
            return order;
        } catch (IllegalArgumentException e) {
            logger.error("The order didn't update", e);
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Order order = manager.find(Order.class, id);
            order.setStatus(Order.Status.CANCELED);
            manager.merge(order);
            manager.clear();
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("The order didn't deleted", e);
            throw new OrderException("The order didn't delete");
        }
    }
}

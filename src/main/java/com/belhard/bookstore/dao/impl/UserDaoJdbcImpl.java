package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.entity.User;
import com.belhard.bookstore.exceptions.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository("userDao")
public class UserDaoJdbcImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoJdbcImpl.class);

    public UserDaoJdbcImpl() {
    }

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<User> findAll(int page, int items, String sortColumn, String direction) {
        List<User> users = manager.createQuery("from User where deleted = false order by ?1 ?2", User.class)
                .setFirstResult(page)
                .setMaxResults(items)
                .setParameter(1, sortColumn)
                .setParameter(2, direction)
                .getResultList();
        manager.clear();
        return users;
    }

    @Override
    public User find(Long id) {
        try {
            User user = manager.find(User.class, id);
            manager.clear();
            return user;
        } catch (NoResultException e) {
            logger.info("The user was not received by id.", e);
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            User user = manager.createQuery("from User where deleted = false and email = ?1", User.class)
                    .setParameter(1, email)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            logger.info("The user was not received by email", e);
            return null;
        }
    }

    @Override
    public List<User> getUserByLastName(String lastName) {
        List<User> users = manager.createQuery("from User where deleted = false and lastName = ?1", User.class)
                .setParameter(1, lastName)
                .getResultList();
        return users;
    }

    @Override
    @Transactional
    public User create(User user) {
        try {
            manager.persist(user);
            manager.clear();
            return user;
        } catch (EntityExistsException | IllegalArgumentException e) {
            logger.error("The user was not created", e);
            return null;
        }
    }

    @Override
    @Transactional
    public User update(User user) {
        try {
            manager.merge(user);
            return user;
        } catch (IllegalArgumentException e) {
            logger.error("The user is not updated.");
            return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        try {
            User user = manager.find(User.class, id);
            user.setDeleted(true);
            manager.merge(user);
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("The user is not deleted.");
            throw new UserException("The user is not deleted.");
        }
    }

    @Override
    @Transactional
    public Long countAllUsers() {
        try {
            Long result = manager.createQuery("SELECT COUNT(*) FROM User where deleted = false", Long.class)
                    .getSingleResult();
            return result;
        } catch (EmptyResultDataAccessException e) {
            logger.error("The users are not counted.", e);
            throw new UserException("The users are not counted");
        }
    }
}

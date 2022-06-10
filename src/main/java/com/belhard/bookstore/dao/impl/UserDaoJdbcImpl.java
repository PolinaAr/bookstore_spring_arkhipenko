package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.entity.Book;
import com.belhard.bookstore.dao.entity.User;
import com.belhard.bookstore.exceptions.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        if (direction.equals("ASC")) {
            query.select(root).where(builder.equal(root.get("deleted"), false)).orderBy(builder.asc(root.get(sortColumn)));
        } else {
            query.select(root).where(builder.equal(root.get("deleted"), false)).orderBy(builder.desc(root.get(sortColumn)));
        }
        Query q = manager.createQuery(query);
        return q.setFirstResult(page).setMaxResults(items).getResultList();
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

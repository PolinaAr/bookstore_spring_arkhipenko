package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.RowMappers.UserRowMapper;
import com.belhard.bookstore.exceptions.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("userDao")
public class UserDaoJdbcImpl implements UserDao {

    private final NamedParameterJdbcTemplate template;
    private final UserRowMapper rowMapper;

    @Autowired
    public UserDaoJdbcImpl(NamedParameterJdbcTemplate template, UserRowMapper rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }

    private static final Logger logger = LogManager.getLogger(UserDaoJdbcImpl.class);

    public static final String GET_ALL = "SELECT u.id, u.name, u.lastname, r.name AS role, u.email, u.password, u.birthday FROM users u " +
            "JOIN roles r ON u.role_id = r.id WHERE u.deleted = false";
    public static final String GET_BY_ID = "SELECT u.id, u.name, u.lastname, r.name AS role, u.email, u.password, u.birthday FROM users u " +
            "JOIN roles r ON u.role_id = r.id WHERE u.id = :id AND u.deleted = false";
    public static final String GET_BY_LAST_NAME = "SELECT u.id, u.name, u.lastname, r.name AS role, u.email, u.password, u.birthday FROM users u " +
            "JOIN roles r ON u.role_id = r.id WHERE u.lastname = :lastname AND u.deleted = false";
    public static final String GET_BY_EMAIL = "SELECT u.id, u.name, u.lastname, r.name AS role, u.email, u.password, u.birthday FROM users u " +
            "JOIN roles r ON u.role_id = r.id WHERE u.email = :email AND u.deleted = false";
    public static final String INSERT = "INSERT INTO users (name, lastname, role_id, email, password, birthday) " +
            "VALUES (:name, :lastname, (SELECT id FROM roles WHERE name = :roleName), :email, :password, :birthday)";
    public static final String UPDATE = "UPDATE users SET name = :name, lastname = :lastname, role_id = (SELECT id FROM roles WHERE name = :roleName), " +
            "email = :email, password = :password, birthday = :birthday WHERE id = :id";
    public static final String DELETE = "UPDATE users SET deleted = true WHERE id= :id";
    public static final String COUNT_ALL_USERS = "SELECT COUNT(*) AS count FROM users WHERE deleted = false";

    @Override
    public List<User> getAllUsers() {
        return template.query(GET_ALL, rowMapper);
    }

    @Override
    public User getUserById(Long id) {
        try {
            return template.queryForObject(GET_BY_ID, Map.of("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The user was not received by id.", e);
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return template.queryForObject(GET_BY_EMAIL, Map.of("email", email), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The user was nat received by email", e);
            return null;
        }
    }

    @Override
    public List<User> getUserByLastName(String lastName) {
        return template.query(GET_BY_LAST_NAME, Map.of("lastname", lastName), rowMapper);
    }

    @Override
    public User createUser(User user) {
        try {
            Map<String, Object> params = getMap(user);
            SqlParameterSource source = new MapSqlParameterSource(params);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsUpdated = template.update(INSERT, source, keyHolder, new String[]{"id"});
            if (rowsUpdated != 1) {
                logger.error("The user didn't created");
                return null;
            }
            Long id = Optional.ofNullable(keyHolder.getKey()).map(Number::longValue).get();
            if (id != 0) {
                return getUserById(id);
            } else {
                logger.error("The user didn't created");
                return null;
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("The user didn't created");
            return null;
        }
    }

    private Map<String, Object> getMap(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("lastname", user.getLastName());
        params.put("roleName", user.getRole().toString());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("birthday", Date.valueOf(LocalDate.of(user.getBirthday().getYear(), user.getBirthday().getMonth(),
                user.getBirthday().getDayOfMonth())));
        return params;
    }

    @Override
    public User updateUser(User user) {
        try {
            Map<String, Object> params = getMap(user);
            params.put("id", user.getId());
            int rowsUpdated = template.update(UPDATE, params);
            if (rowsUpdated != 1) {
                logger.error("The user didn't updated");
                return null;
            }
            return getUserById(user.getId());
        } catch (EmptyResultDataAccessException e) {
            logger.error("The user is not updated.");
            return null;
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            int result = template.update(DELETE, Map.of("id", id));
            return result == 1;
        } catch (EmptyResultDataAccessException e) {
            logger.error("The user is not deleted.");
        }
        throw new UserException("The user is not deleted.");
    }

    @Override
    public int countAllUsers() {
        try {
            List<Integer> result = template.query(COUNT_ALL_USERS, (rs, rowNum) -> rs.getInt("count"));
            return result.get(0);
        } catch (EmptyResultDataAccessException e) {
            logger.error("The users are not counted.", e);
            throw new UserException("The users are not counted");
        }
    }
}

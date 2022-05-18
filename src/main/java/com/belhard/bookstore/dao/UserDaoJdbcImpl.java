package com.belhard.bookstore.dao;

import com.belhard.bookstore.exceptions.UserException;
import com.belhard.bookstore.util.DbConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {
    }

    private static final Logger logger = LogManager.getLogger(UserDaoJdbcImpl.class);

    public static final String GET_ALL = "SELECT u.id, u.name, u.lastname, r.name AS role, u.email, u.password, u.birthday FROM users u " +
            "JOIN roles r ON u.role_id = r.id WHERE u.deleted = false";
    public static final String GET_BY_ID = "SELECT u.id, u.name, u.lastname, r.name AS role, u.email, u.password, u.birthday FROM users u " +
            "JOIN roles r ON u.role_id = r.id WHERE u.id = ? AND u.deleted = false";
    public static final String GET_BY_LAST_NAME = "SELECT u.id, u.name, u.lastname, r.name AS role, u.email, u.password, u.birthday FROM users u " +
            "JOIN roles r ON u.role_id = r.id WHERE u.lastname = ? AND u.deleted = false";
    public static final String GET_BY_EMAIL = "SELECT u.id, u.name, u.lastname, r.name AS role, u.email, u.password, u.birthday FROM users u " +
            "JOIN roles r ON u.role_id = r.id WHERE u.email = ? AND u.deleted = false";
    public static final String INSERT = "INSERT INTO users (name, lastname, role_id, email, password, birthday) " +
            "VALUES (?, ?, (SELECT id FROM roles WHERE name = ?), ?, ?, ?)";
    public static final String UPDATE = "UPDATE users SET name = ?, lastname = ?, role_id = (SELECT id FROM roles WHERE name = ?), " +
            "email = ?, password = ?, birthday = ? WHERE id= ?";
    public static final String DELETE = "UPDATE users SET deleted = true WHERE id= ?";
    public static final String COUNT_ALL_USERS = "SELECT COUNT(*) AS count FROM users WHERE deleted = false";

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                users.add(processResultGet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("The list of users was not received.", e);
        }
        return users;
    }

    private User processResultGet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("lastname"));
        user.setRole(User.Role.valueOf(resultSet.getString("role")));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setBirthday(resultSet.getDate("birthday").toLocalDate());
        return user;
    }

    @Override
    public User getUserById(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return processResultGet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("The user was not received by id.", e);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return processResultGet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("The user was not received by email.", e);
        }
        return null;
    }

    @Override
    public List<User> getUserByLastName(String lastName) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BY_LAST_NAME);
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(processResultGet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("The list of users was not received by last name.", e);
        }
        return users;
    }

    @Override
    public User createUser(User user) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            prepareStatementUser(user, statement);
            int result = statement.executeUpdate();
            if (result != 1) {
                throw new UserException("User didn't create.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return getUserById(generatedKeys.getLong("id"));
            }
        } catch (SQLException e) {
            logger.error("The user was not created.", e);
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(UPDATE);
            prepareStatementUser(user, statement);
            statement.setLong(7, user.getId());
            int result = statement.executeUpdate();
            if (result != 1) {
                throw new UserException("The User didn't update.");
            }
            return getUserById(user.getId());
        } catch (SQLException e) {
            logger.error("The user is not updated.");
        }
        return null;
    }

    private void prepareStatementUser(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getRole().toString());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPassword());
        statement.setDate(6, Date.valueOf(LocalDate.of(user.getBirthday().getYear(),
                user.getBirthday().getMonth(), user.getBirthday().getDayOfMonth())));
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            logger.error("The user is not deleted.");
        }
        throw new UserException("The user is not deleted.");
    }

    @Override
    public int countAllUsers() {
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL_USERS);
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("The users are not counted.", e);
        }
        throw new UserException("The users are not counted");
    }
}

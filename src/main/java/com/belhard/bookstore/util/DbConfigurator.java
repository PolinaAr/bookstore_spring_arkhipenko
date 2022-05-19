package com.belhard.bookstore.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfigurator {
    private static final Logger logger = LogManager.getLogger(DbConfigurator.class);
    private static Connection connection;
    @Value("${driver-class-name}")
    private static String driverName;
    @Value("${url}")
    private static String url;
    @Value("${username}")
    private static String user;
    @Value("${password}")
    private static String password;

    public static void initDbConnection() {
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Connection created");
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            logger.error("The connection was not established", e);
            System.out.println("The connection was nat established. Please try again.");
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            initDbConnection();
        }
        logger.debug("Appealing to the database");
        return connection;
    }

    @PreDestroy
    public void destroy() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

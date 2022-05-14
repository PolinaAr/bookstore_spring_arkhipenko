package com.belhard.bookstore.util;

import com.belhard.bookstore.util.ResourceReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DbConfigurator {
    private static final Logger logger = LogManager.getLogger(DbConfigurator.class);
    private static ResourceReader resourceReader = new ResourceReader();
    private static Connection connection;
    private static String url = resourceReader.getBdUrl();
    private static String user = resourceReader.getDbUser();
    private static String password = resourceReader.getDbPassword();

    public static void initDbConnection() {
        try {
            Class.forName("org.postgresql.Driver");
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

}

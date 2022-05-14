package com.belhard.bookstore.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

public class ResourceReader {

    private static final Logger logger = LogManager.getLogger(ResourceReader.class);
    public static final String CONDITION_BASE = "Enter \"local\" or \"remote\" to choose local or remote base";
    private static final String LOCAL_BASE = "C:\\Users\\User\\IdeaProjects\\bookstore_arkhipenko\\src\\main" +
            "\\resources\\properties\\db.properties";
    private static final String REMOTE_BASE = "C:\\Users\\User\\IdeaProjects\\bookstore_arkhipenko\\src\\main" +
            "\\resources\\properties\\remoteDb.properties";
    private static Properties properties;

    public ResourceReader() {
        System.out.println(CONDITION_BASE);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(chooseBase()));
            properties = new Properties();
            properties.load(reader);
        } catch (IOException e) {
            logger.error("The database information file was not read", e);
        }
    }

    private String chooseBase() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String base = scanner.next().toLowerCase(Locale.ROOT);
            switch (base) {
                case "local":
                    return LOCAL_BASE;
                case "remote":
                    return REMOTE_BASE;
                default:
                    System.out.println("Illegal input. Please try again." + CONDITION_BASE);
            }
        }
    }

    public String getDbUser() {
        String user = properties.getProperty("db.user");
        return user;
    }

    public String getDbPassword() {
        String password = properties.getProperty("db.password");
        return password;
    }

    public String getBdUrl() {
        String url = properties.getProperty("db.url");
        return url;
    }
}

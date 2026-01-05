package com.cht.travelmanagement.Models;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseDriver {
    private static final Logger logger = Logger.getLogger(DatabaseDriver.class.getName());
    private static final Properties properties = new Properties();
    private static String dbDriver;
    private static String dbUrl;
    private static String dbUser;
    private static String dbPass;

    static {
        try (InputStream input = DatabaseDriver.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.log(Level.SEVERE, "Sorry, unable to find config.properties");
            } else {
                properties.load(input);
                dbDriver = properties.getProperty("db.driver");
                dbUrl = properties.getProperty("db.url");
                dbUser = properties.getProperty("db.user");
                dbPass = properties.getProperty("db.password");
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading configuration", ex);
        }
    }

    public DatabaseDriver() {}

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Database Driver not found: " + e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection Failed: " + e.getMessage());
            throw e;
        }
        return connection;
    }
}
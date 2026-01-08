package com.cht.travelmanagement.Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class DatabaseInitializer {

    public static void initialize() {
        File scriptFile = new File("schema.sql");
        if (!scriptFile.exists()) {
            System.out.println("No schema.sql found. Skipping auto-initialization.");
            return;
        }

        System.out.println("Found schema.sql! checking database...");

        Properties props = new Properties();
        InputStream input = null;
        try {
            File externalConfig = new File("config.properties");
            if (externalConfig.exists()) {
                input = new FileInputStream(externalConfig);
            } else {
                input = DatabaseInitializer.class.getClassLoader().getResourceAsStream("config.properties");
            }

            if (input == null) {
                System.out.println("Could not find config.properties. Cannot init DB.");
                return;
            }
            props.load(input);
        } catch (Exception e) {
            System.out.println("Error loading config: " + e.getMessage());
            return;
        } finally {
            if (input != null) {
                try { input.close(); } catch (Exception e) {}
            }
        }

        String dbUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        if (dbUrl == null || dbUrl.isEmpty()) return;

        String serverUrl = dbUrl.substring(0, dbUrl.lastIndexOf("/"));
        String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
        if (dbName.contains("?")) {
            dbName = dbName.substring(0, dbName.indexOf("?"));
        }

        try (Connection conn = DriverManager.getConnection(serverUrl + "?useSSL=false&allowPublicKeyRetrieval=true", user, pass); 
             Statement stmt = conn.createStatement()) {

            // Ensure DB exists
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
            stmt.execute("USE " + dbName);

            // Check if tables exist
            DatabaseMetaData dbm = conn.getMetaData();
            try (ResultSet rs = dbm.getTables(dbName, null, "employee", null)) {
                if (rs.next()) {
                    System.out.println("Database already initialized (tables found). Skipping script execution.");
                    return;
                }
            }

            System.out.println("Initializing database tables...");
            try (Scanner scanner = new Scanner(scriptFile)) {

                scanner.useDelimiter(";");

                while (scanner.hasNext()) {
                    String rawCommand = scanner.next().trim();

                    // Strip leading comment lines (lines starting with --)
                    while (rawCommand.startsWith("--")) {
                        int newlineIndex = rawCommand.indexOf('\n');
                        if (newlineIndex == -1) {
                            rawCommand = "";
                            break;
                        }
                        rawCommand = rawCommand.substring(newlineIndex + 1).trim();
                    }

                    if (rawCommand.isEmpty()) {
                        continue;
                    }

                    if (rawCommand.contains("Dump completed")) {
                        break;
                    }

                    try {

                        stmt.execute(rawCommand);

                        if (rawCommand.toUpperCase().startsWith("CREATE")) {
                            System.out.println("Executed: " + rawCommand.split("\n")[0]);
                        }
                    } catch (Exception e) {
                        if (!e.getMessage().contains("exists")) {
                            System.out.println("Warning executing command: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Database initialization complete.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Critical Error during DB Init: " + e.getMessage());
        }
    }
}

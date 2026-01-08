package com.cht.seeder;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseSeeder {

    public static void main(String[] args) {
        DatabaseSeeder seeder = new DatabaseSeeder();
        seeder.run();
    }

    public void run() {
        Properties props = loadConfig();
        if (props == null) return;

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String baseUrl = url.substring(0, url.lastIndexOf("/") + 1);
            String dbName = url.substring(url.lastIndexOf("/") + 1);
            if (dbName.contains("?")) {
                dbName = dbName.substring(0, dbName.indexOf("?"));
            }

            System.out.println("Connecting to MySQL Server...");
            try (Connection serverConn = DriverManager.getConnection(baseUrl + "?useSSL=false&allowPublicKeyRetrieval=true", user, password);
                 Statement stmt = serverConn.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
                System.out.println("Database '" + dbName + "' verified/created.");
            }

            System.out.println("Connecting to database: " + url);
            try (Connection conn = DriverManager.getConnection(url + (url.contains("?") ? "&" : "?") + "useSSL=false&allowPublicKeyRetrieval=true", user, password)) {
                System.out.println("--- Connected Successfully ---");

                if (!tablesExist(conn)) {
                    System.err.println("Error: Tables not found. Please run schema.sql first.");
                    return;
                }

                int adminId = seedAdmin(conn);

                if (adminId != -1) {
                    seedAllData(conn, adminId);
                }

                System.out.println("--- Seeding Completed Successfully ---");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found!");
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
        }
    }

    private boolean tablesExist(Connection conn) {
        try {
            DatabaseMetaData dbm = conn.getMetaData();
            try (ResultSet tables = dbm.getTables(null, null, "employee", null)) {
                return tables.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    private Properties loadConfig() {
        Properties props = new Properties();
        java.io.File externalFile = new java.io.File("config.properties");
        if (externalFile.exists()) {
            try (java.io.FileInputStream fis = new java.io.FileInputStream(externalFile)) {
                props.load(fis);
                System.out.println("Using external configuration.");
                return props;
            } catch (Exception e) {
                System.err.println("Error reading external config: " + e.getMessage());
            }
        }

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) return null;
            props.load(input);
            return props;
        } catch (Exception ex) {
            return null;
        }
    }

    private int seedAdmin(Connection conn) throws SQLException {
        String email = "admin@cht.com";
        String checkSql = "SELECT employeeId FROM employee WHERE email = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) return rs.getInt("employeeId");
        }

        String insertSql = "INSERT INTO employee (name, email, password, contactNumber, isManager, isActive) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "Super Admin");
            pstmt.setString(2, email);
            pstmt.setString(3, "cbfdac6008f9cab4083784cbd1874f76618d2a97");
            pstmt.setString(4, "09123456789");
            pstmt.setBoolean(5, true);
            pstmt.setBoolean(6, true);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    private void seedAllData(Connection conn, int adminId) throws SQLException {
        // Data derived from @docs/Find Packages_Information about CHT.md
        seedPackageWithDetails(conn, adminId, 
            "Hokkaido Icebreaker", "Sail through drift ice and enjoy Sapporo Snow Festival.", "Hokkaido, Japan", 6, 30, 2288.0,
            new String[]{"Bibai Snowland", "Monbetsu Icebreaker Cruise", "Asahiyama Zoo Penguin Parade", "Sapporo Snow Festival"},
            "Hokkaido Grand Hotel", "Icebreaker Ship & Coach");

        seedPackageWithDetails(conn, adminId, 
            "Hong Kong & Macau Getaway", "Experience dazzling skylines and world-class attractions.", "Hong Kong", 4, 40, 449.0,
            new String[]{"Victoria Peak", "Disneyland Tour", "Macau Bridge Crossing", "Avenue of Stars"},
            "Panda Hotel Hong Kong", "TurboJet Ferry");

        seedPackageWithDetails(conn, adminId, 
            "Bali Winter Tour", "Explore the beauty of Bali during the holidays.", "Bali, Indonesia", 4, 20, 28888.0,
            new String[]{"Ulun Danu Temple", "Tanah Lot Temple", "D Tuked Waterfall"},
            "Bali Garden Resort", "Private Van");

        seedPackageWithDetails(conn, adminId, 
            "Taiwan Taipei + Taichung", "Taipei lights, Taichung sights adventure.", "Taiwan", 4, 13, 27988.0,
            new String[]{"Sun Moon Lake Cruise", "Taipei 101", "Yehliu Geopark", "Shifen Sky Lantern"},
            "Taichung City Inn", "Tour Bus");
            
        seedPackageWithDetails(conn, adminId, 
            "Naga to Baguio", "Our Best Seller: Private tour to the Summer Capital.", "Baguio, Philippines", 3, 13, 3999.0,
            new String[]{"Burnham Park", "Strawberry Farm", "Mines View Park", "Igorot Stone Kingdom"},
            "Baguio Transient House", "Toyota Hiace Van");
    }

    private void seedPackageWithDetails(Connection conn, int adminId, String name, String desc, String dest, int duration, int maxPax, double price, String[] activities, String hotel, String vehicle) throws SQLException {
        // 1. Seed Package
        int packageId = -1;
        String checkPkg = "SELECT PackageID FROM package WHERE Name = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkPkg)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                packageId = rs.getInt(1);
                System.out.println("Package '" + name + "' already exists. Updating details...");
            }
        }

        if (packageId == -1) {
            String insPkg = "INSERT INTO package (Name, Description, Destination, Duration, MaxPax, Inclusions, Price, IsActive, CreatedByEmployeeID) VALUES (?, ?, ?, ?, ?, ?, ?, 1, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insPkg, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name);
                ps.setString(2, desc);
                ps.setString(3, dest);
                ps.setInt(4, duration);
                ps.setInt(5, maxPax);
                ps.setString(6, String.join(", ", activities));
                ps.setDouble(7, price);
                ps.setInt(8, adminId);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) packageId = rs.getInt(1);
            }
        }

        // 2. Seed Master Trip for this package
        int tripId = -1;
        String tripName = "Trip: " + name;
        if (tripName.length() > 40) tripName = tripName.substring(0, 40);
        
        String insTrip = "INSERT INTO trip (Name, Description, Location, StartDate, EndDate, IsActive) VALUES (?, ?, ?, ?, ?, 1)";
        try (PreparedStatement ps = conn.prepareStatement(insTrip, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tripName);
            ps.setString(2, "Automatic trip for " + name);
            ps.setString(3, dest);
            ps.setDate(4, Date.valueOf("2026-01-01"));
            ps.setDate(5, Date.valueOf("2026-01-07"));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) tripId = rs.getInt(1);
        }

        // 3. Link Trip to Package
        String insLink = "INSERT INTO packagetrips (PackageID, TripID, Sequence, DayOfPackage) VALUES (?, ?, 1, 1)";
        try (PreparedStatement ps = conn.prepareStatement(insLink)) {
            ps.setInt(1, packageId);
            ps.setInt(2, tripId);
            ps.executeUpdate();
        }

        // 4. Seed Activities
        for (String actName : activities) {
            String safeActName = actName.length() > 50 ? actName.substring(0, 50) : actName;
            int actId = getOrSeedActivity(conn, safeActName);
            String linkAct = "INSERT INTO tripsactivities (TripID, ActivityID, IsIncluded) VALUES (?, ?, 1)";
            try (PreparedStatement ps = conn.prepareStatement(linkAct)) {
                ps.setInt(1, tripId);
                ps.setInt(2, actId);
                ps.executeUpdate();
            }
        }

        // 5. Seed Accommodation
        String safeHotelName = hotel.length() > 40 ? hotel.substring(0, 40) : hotel;
        int accId = getOrSeedAccommodation(conn, safeHotelName, dest);
        String linkAcc = "INSERT INTO tripaccomodations (TripID, AccommodationID, IsIncluded) VALUES (?, ?, 1)";
        try (PreparedStatement ps = conn.prepareStatement(linkAcc)) {
            ps.setInt(1, tripId);
            ps.setInt(2, accId);
            ps.executeUpdate();
        }

        // 6. Seed Vehicle
        int vehId = getOrSeedVehicle(conn, vehicle);
        String linkVeh = "INSERT INTO tripvehicles (TripID, VehicleID, IsIncluded) VALUES (?, ?, 1)";
        try (PreparedStatement ps = conn.prepareStatement(linkVeh)) {
            ps.setInt(1, tripId);
            ps.setInt(2, vehId);
            ps.executeUpdate();
        }
        
        System.out.println("Successfully seeded package: " + name + " with trip, activities, and logistics.");
    }

    private int getOrSeedActivity(Connection conn, String name) throws SQLException {
        String check = "SELECT activityId FROM activity WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(check)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        String ins = "INSERT INTO activity (name, description, isIncludedByDefault) VALUES (?, ?, 1)";
        try (PreparedStatement ps = conn.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, "Activity part of package");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    private int getOrSeedAccommodation(Connection conn, String name, String loc) throws SQLException {
        String check = "SELECT accommodationId FROM accommodation WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(check)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        String ins = "INSERT INTO accommodation (name, address, numberOfRooms) VALUES (?, ?, 10)";
        try (PreparedStatement ps = conn.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, loc);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    private int getOrSeedVehicle(Connection conn, String type) throws SQLException {
        String check = "SELECT VehicleID FROM vehicle WHERE Type = ?";
        try (PreparedStatement ps = conn.prepareStatement(check)) {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        String ins = "INSERT INTO vehicle (Type, Capacity, ProviderName) VALUES (?, 45, 'CHT Logistics')";
        try (PreparedStatement ps = conn.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, type);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }
}
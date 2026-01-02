package com.cht.travelmanagement.Models.Repository.Implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.cht.travelmanagement.Models.Booking;
import com.cht.travelmanagement.Models.DatabaseDriver;
import com.cht.travelmanagement.Models.Repository.BookingRepository;

import com.cht.travelmanagement.Models.Repository.ClientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookingRepositoryImpl implements BookingRepository {
    public BookingRepositoryImpl() {
    }

    /**
     * Fetches recent bookings from the database.
     *
     * @return An ObservableList of Booking objects representing recent bookings.
     */
    @Override
    public ObservableList<Booking> getRecentBookings() {
        ObservableList<Booking> recentBookings = FXCollections.observableArrayList();
        String query = "SELECT b.BookingID AS TripID, c.name AS CustomerName, p.Name AS PackageName, p.Destination, MIN(t.StartDate) AS StartDate, " +
                "CASE WHEN MAX(t.EndDate) < CURRENT_DATE THEN 'Completed' " +
                "WHEN MIN(t.StartDate) <= CURRENT_DATE AND MAX(t.EndDate) >= CURRENT_DATE THEN 'Ongoing' " +
                "ELSE 'Upcoming' END AS Status " +
                "FROM booking b " +
                "JOIN client c ON b.ClientID = c.clientId " +
                "JOIN package p ON b.PackageID = p.PackageID " +
                "JOIN packagetrips pt ON p.PackageID = pt.PackageID " +
                "JOIN trip t ON pt.TripID = t.TripID " +
                "WHERE b.Status = 'confirmed' " +
                "GROUP BY b.BookingID, c.name, p.Name, p.Destination " +
                "ORDER BY StartDate ASC " +
                "LIMIT 10;";
        return getBookings(recentBookings, query);

    }

    @Override
    public ObservableList<Booking> getAllBookings() {
        ObservableList<Booking> allBookings = FXCollections.observableArrayList();
        String query = "SELECT b.BookingID AS TripID, c.name AS CustomerName, p.Name AS PackageName, p.Destination, MIN(t.StartDate) AS StartDate, " +
                "CASE WHEN MAX(t.EndDate) < CURRENT_DATE THEN 'Completed' " +
                "WHEN MIN(t.StartDate) <= CURRENT_DATE AND MAX(t.EndDate) >= CURRENT_DATE THEN 'Ongoing' " +
                "ELSE 'Upcoming' END AS Status " +
                "FROM booking b " +
                "JOIN client c ON b.ClientID = c.clientId " +
                "JOIN package p ON b.PackageID = p.PackageID " +
                "JOIN packagetrips pt ON p.PackageID = pt.PackageID " +
                "JOIN trip t ON pt.TripID = t.TripID " +
                "WHERE b.Status = 'confirmed' " +
                "GROUP BY b.BookingID, c.name, p.Name, p.Destination " +
                "ORDER BY StartDate ASC;";
        return getBookings(allBookings, query);
    }

    private ObservableList<Booking> getBookings(ObservableList<Booking> allBookings, String query) {
        try (Connection connection = DatabaseDriver.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int bookingId = resultSet.getInt("TripID");
                String clientName = resultSet.getString("CustomerName");
                String destination = resultSet.getString("Destination");
                String packageName = resultSet.getString("PackageName");
                LocalDate startDate = resultSet.getDate("StartDate").toLocalDate();
                String status = resultSet.getString("Status");

                Booking booking = new Booking(bookingId, clientName, destination, packageName, startDate, status);
                allBookings.add(booking);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allBookings;
    }


    @Override
    public int[] getDashboardData(int clientCount) {
        int[] dashboardData = new int[4];

        System.out.println("Total Clients: " + clientCount);

        String bookingDatesQuery = "SELECT \r\n" + //
                "    SUM(IF(TripEndDate < CURRENT_DATE, 1, 0)) AS CompletedTrips,\r\n" + //
                "    SUM(IF(TripStartDate <= CURRENT_DATE AND TripEndDate >= CURRENT_DATE, 1, 0)) AS OngoingTrips,\r\n" + //
                "    SUM(IF(TripStartDate > CURRENT_DATE, 1, 0)) AS UpcomingTrips\r\n" + //
                "FROM (\r\n" + //
                "    SELECT \r\n" + //
                "        MIN(T.StartDate) AS TripStartDate,\r\n" + //
                "        MAX(T.EndDate) AS TripEndDate\r\n" + //
                "    FROM booking B \r\n" + //
                "        JOIN packagetrips PT ON B.PackageID = PT.PackageID\r\n" + //
                "        JOIN trip T ON PT.TripID = T.TripID\r\n" + //
                "        WHERE B.Status = 'Confirmed'\r\n" + //
                "    GROUP BY B.BookingID\r\n" + //
                "\r\n" + //
                ") AS BookingDates;";
        try (Connection conn = DatabaseDriver.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(bookingDatesQuery);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int completedTrips = rs.getInt("CompletedTrips");
                int ongoingTrips = rs.getInt("OngoingTrips");
                int upcomingTrips = rs.getInt("UpcomingTrips");

                dashboardData[0] = clientCount;
                dashboardData[1] = completedTrips;
                dashboardData[2] = ongoingTrips;
                dashboardData[3] = upcomingTrips;

                System.out.println("Completed Trips: " + completedTrips);
                System.out.println("Ongoing Trips: " + ongoingTrips);
                System.out.println("Upcoming Trips: " + upcomingTrips);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dashboardData;
    }


}

package com.cht.travelmanagement.Models.Repository.Implementation;

import com.cht.travelmanagement.Models.DatabaseDriver;
import com.cht.travelmanagement.Models.Repository.TourPackageRepository;
import com.cht.travelmanagement.Models.TourPackage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TourPackageRepositoryImpl implements TourPackageRepository{
    public final ObservableList<TourPackage> tourPackages;

    public TourPackageRepositoryImpl() {
        this.tourPackages = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<TourPackage> getTourPackages() {
        String query = "SELECT * FROM package";
        tourPackages.clear();
        try (Connection connection = DatabaseDriver.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int packageId = resultSet.getInt("PackageId");
                String packageName = resultSet.getString("Name");
                String description = resultSet.getString("Description");
                String destination = resultSet.getString("Destination");
                int durationDays = resultSet.getInt("Duration");
                int maxParticipants = resultSet.getInt("MaxPax");
                String inclusions = resultSet.getString("Inclusions");
                int price = resultSet.getInt("Price");
                boolean isActive = resultSet.getBoolean("IsActive");
                int createdBy = resultSet.getInt("CreatedByEmployeeId");

                TourPackage tourPackage = new TourPackage(packageId, packageName, description, destination,
                        durationDays, maxParticipants, inclusions, price, isActive, createdBy);

                tourPackages.add(tourPackage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return tourPackages;
    }
}

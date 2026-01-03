package com.cht.travelmanagement.Models.Repository.Implementation;

import com.cht.travelmanagement.Models.DatabaseDriver;
import com.cht.travelmanagement.Models.Model;
import com.cht.travelmanagement.Models.Repository.EmployeeRepository;
import com.cht.travelmanagement.View.AccountType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRepositoryImpl implements EmployeeRepository{

    @Override
    public void evaluateLoginCredentials(String email, String password, AccountType accountType, boolean userLoggedInSuccessfully) {
        String verifyLogin = "SELECT COUNT(1) FROM employee WHERE email = ? AND  password = ?";

        if (accountType == AccountType.ADMIN) {
            verifyLogin += " AND isManager = 1";
        }
        try (Connection connection = DatabaseDriver.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(verifyLogin);) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    if (resultSet.getInt(1) == 1) {
                        Model.getInstance().setUserLoggedInSuccessfully(true);
                    } else {
                        System.out.println("Invalid Login. Please try again.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

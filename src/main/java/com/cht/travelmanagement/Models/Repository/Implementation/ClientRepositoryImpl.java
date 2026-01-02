package com.cht.travelmanagement.Models.Repository.Implementation;

import com.cht.travelmanagement.Models.Client;
import com.cht.travelmanagement.Models.DatabaseDriver;
import com.cht.travelmanagement.Models.Repository.ClientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class ClientRepositoryImpl implements ClientRepository {
    public final ObservableList<Client> clients;

    public ClientRepositoryImpl() {
        this.clients = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Client> getClients() {
        String query = "SELECT * FROM client";
        clients.clear();
        try (Connection connection = DatabaseDriver.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int clientId = resultSet.getInt("clientId");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String contactNumber = resultSet.getString("contactNumber");
                String customerType = resultSet.getString("customerType");
                Date sqlDateRegistered = resultSet.getDate("dateRegistered");
                LocalDate dateRegistered = sqlDateRegistered.toLocalDate();

                Client client = new Client(clientId, name, email, address, contactNumber, customerType, dateRegistered);

                clients.add(client);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

}

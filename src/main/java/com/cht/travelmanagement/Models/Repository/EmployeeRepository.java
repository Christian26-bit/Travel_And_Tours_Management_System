package com.cht.travelmanagement.Models.Repository;

import com.cht.travelmanagement.Models.Client;
import com.cht.travelmanagement.View.AccountType;

import javafx.collections.ObservableList;

public interface EmployeeRepository {
    public void evaluateLoginCredentials(String email, String password, AccountType accountType, boolean userLoggedInSuccessfully);

    public void getAuthenticatedUser();

    public ObservableList<Client> getEmployeeClientList(int employeeId);


}

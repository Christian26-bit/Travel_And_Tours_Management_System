package com.cht.travelmanagement.Models.Repository;

import com.cht.travelmanagement.View.AccountType;

public interface EmployeeRepository {
    public void evaluateLoginCredentials(String email, String password, AccountType accountType, boolean userLoggedInSuccessfully);
}

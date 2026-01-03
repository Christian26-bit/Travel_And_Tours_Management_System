package com.cht.travelmanagement.Models;

import javafx.beans.property.*;

public class Employee {
    private IntegerProperty employeeId;
    private StringProperty name;
    private StringProperty email;
    private StringProperty password;
    private StringProperty contactNumber;
    private BooleanProperty isManager;
    private BooleanProperty isActive;

    public Employee(int employeeId, String name, String email, String password, String contactNumber, boolean isManager, boolean isActive) {
        this.employeeId = new SimpleIntegerProperty(this, "employeeId", employeeId);
        this.name = new SimpleStringProperty(this, "name", name);
        this.email = new SimpleStringProperty(this, "email", email);
        this.password = new SimpleStringProperty(this, "password", password);
        this.contactNumber = new SimpleStringProperty(this, "contactNumber", contactNumber);
        this.isManager = new SimpleBooleanProperty(this, "isManager", isManager);
        this.isActive = new SimpleBooleanProperty(this, "isActive", isActive);
    }

    public  int getEmployeeId() { return employeeId.get(); }
    public IntegerProperty employeeIdProperty() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId.set(employeeId); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
    public void setEmail(String email) { this.email.set(email); }

    public String getPassword() { return password.get(); }
    public StringProperty passwordProperty() { return password; }
    public void setPassword(String password) { this.password.set(password); }

    public String getContactNumber() { return contactNumber.get(); }
    public StringProperty contactNumberProperty() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber.set(contactNumber); }

    public boolean isManager() { return isManager.get(); }
    public BooleanProperty isManagerProperty() { return isManager; }
    public void setIsManager(boolean isManager) { this.isManager.set(isManager); }

    public boolean isActive() { return isActive.get(); }
    public BooleanProperty isActiveProperty() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive.set(isActive); }




}

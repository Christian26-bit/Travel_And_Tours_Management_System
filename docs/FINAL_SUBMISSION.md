# FINAL PROJECT DOCUMENTATION
## Travel Management System

---

### 1. Title Page

**Project Title:** Travel Management System (v2.0)
**Submission Date:** January 9, 2026

**Group Members:**
*   [Member Name 1]
*   [Member Name 2]
*   [Member Name 3]
*   [Member Name 4]

---

### 2. Table of Contents

1.  Project Background
2.  Data Model (ERD)
3.  Relational Model
4.  Source Codes
5.  SQL Scripts
6.  User Interfaces

---

### 3. Project Background

**Overview**
The Travel Management System is a comprehensive desktop application designed to streamline the operations of a travel agency. It facilitates the management of clients, tour packages, employee records, and booking transactions. The system is built using JavaFX for the graphical user interface and MySQL for data persistence, ensuring a robust and user-friendly experience for both administrators and staff.

**Objectives**
*   To automate the booking process, reducing manual errors and processing time.
*   To provide a centralized database for managing client and employee information.
*   To enable efficient management of tour packages, including itineraries and pricing.
*   To generate real-time reports on sales and booking status.

**Scope**
The system includes two main modules:
*   **Admin Module:** Allows management of employees, packages, and overall system settings.
*   **User Module (Staff):** Facilitates client registration, booking creation (via a wizard interface), and payment processing.

---

### 4. Data Model (Entity-Relationship Diagram)

*(Note: Please insert your Entity-Relationship Diagram (ERD) image here. You can generate one using tools like MySQL Workbench or draw.io based on the schema below.)*

[INSERT ER DIAGRAM IMAGE HERE]

**Entities:**
*   **Employee:** System users (Admins/Staff).
*   **Client:** Customers booking the trips.
*   **Package:** Tour packages offered by the agency.
*   **Booking:** The transaction linking a Client to a Package handled by an Employee.
*   **Payment:** Financial records linked to a Booking.
*   **Trip:** Specific itinerary details.

---

### 5. Relational Model

**Relational Schema**

*   **Employee** (`employeeId` (PK), `name`, `email`, `password`, `contactNumber`, `isManager`, `isActive`)
*   **Client** (`clientId` (PK), `name`, `email`, `address`, `contactNumber`, `customerType`, `dateRegistered`)
*   **Package** (`PackageID` (PK), `Name`, `Description`, `Destination`, `Duration`, `MaxPax`, `Price`, `IsActive`)
*   **Booking** (`BookingID` (PK), `EmployeeID` (FK), `ClientID` (FK), `PackageID` (FK), `BookingDate`, `Status`, `PaxCount`)
*   **Payment** (`paymentId` (PK), `bookingId` (FK), `amount`, `paymentDate`, `method`, `status`)

*(Refer to `schema.sql` in the SQL Scripts section for full DDL)*

---

### 6. Source Codes

*(Organized by Key Modules. Note: This is a selection of core files. Please refer to the submitted ZIP file for the complete codebase.)*

#### A. Core Models (Entities)

**`Client.java`**
```java
package com.cht.travelmanagement.Models;

import java.time.LocalDate;
import javafx.beans.property.*;

public class Client extends Person {
    private final IntegerProperty clientId;
    private final StringProperty address;
    private final StringProperty customerType;
    private final ObjectProperty<LocalDate> dateRegistered;
    private final StringProperty destination;
    private final StringProperty tripStatus;
    private final StringProperty tripDates;

    public Client(int clientId, String name, String email, String address, String contactNumber, String customerType, LocalDate dateRegistered) {
        super(name, email, contactNumber);
        this.clientId = new SimpleIntegerProperty(this, "clientId", clientId);
        this.address = new SimpleStringProperty(this, "address", address);
        this.customerType = new SimpleStringProperty(this, "customerType", customerType);
        this.dateRegistered = new SimpleObjectProperty<>(this, "dateRegistered", dateRegistered);
        this.destination = new SimpleStringProperty(this, "destination", "");
        this.tripStatus = new SimpleStringProperty(this, "tripStatus", "");
        this.tripDates = new SimpleStringProperty(this, "tripDates", "");
    }
    // Getters and Setters omitted for brevity...
}
```

**`Employee.java`**
```java
package com.cht.travelmanagement.Models;

import javafx.beans.property.*;

public class Employee extends Person {
    private final IntegerProperty employeeId;
    private final StringProperty password;
    private final BooleanProperty isManager;
    private final BooleanProperty isActive;

    public Employee(int employeeId, String name, String email, String password, String contactNumber, boolean isManager, boolean isActive) {
        super(name, email, contactNumber);
        this.employeeId = new SimpleIntegerProperty(this, "employeeId", employeeId);
        this.password = new SimpleStringProperty(this, "password", password);
        this.isManager = new SimpleBooleanProperty(this, "isManager", isManager);
        this.isActive = new SimpleBooleanProperty(this, "isActive", isActive);
    }
    // Getters and Setters omitted for brevity...
}
```

**`TourPackage.java`**
```java
package com.cht.travelmanagement.Models;

import javafx.beans.property.*;

public class TourPackage {
    private final IntegerProperty packageId;
    private final StringProperty packageName;
    private final StringProperty description;
    private final StringProperty destination;
    private final IntegerProperty durationDays;
    private final IntegerProperty maxParticipants;
    private final StringProperty inclusions;
    private final IntegerProperty price;
    private final BooleanProperty isActive;
    private final IntegerProperty createdBy;
    private final StringProperty imagePath;

    public TourPackage(int packageId, String packageName, String description, String destination,
            int durationDays, int maxParticipants, String inclusions, int price, boolean isActive, int createdBy, String imagePath) {
        this.packageId = new SimpleIntegerProperty(this, "packageId", packageId);
        this.packageName = new SimpleStringProperty(this, "packageName", packageName);
        this.description = new SimpleStringProperty(this, "description", description);
        this.destination = new SimpleStringProperty(this, "destination", destination);
        this.durationDays = new SimpleIntegerProperty(this, "durationDays", durationDays);
        this.maxParticipants = new SimpleIntegerProperty(this, "maxParticipants", maxParticipants);
        this.inclusions = new SimpleStringProperty(this, "inclusions", inclusions);
        this.price = new SimpleIntegerProperty(this, "price", price);
        this.isActive = new SimpleBooleanProperty(this, "isActive", isActive);
        this.createdBy = new SimpleIntegerProperty(this, "createdBy", createdBy);
        this.imagePath = new SimpleStringProperty(this, "imagePath", imagePath);
    }
    // Getters and Setters omitted for brevity...
}
```

**`Booking.java`**
```java
package com.cht.travelmanagement.Models;

import java.time.LocalDate;
import javafx.beans.property.*;

public class Booking {
    private final IntegerProperty bookingId;
    private final IntegerProperty employeeId;
    private final IntegerProperty clientId;
    private final StringProperty packageName;
    private final StringProperty clientName;
    private final IntegerProperty packageId;
    private final StringProperty destination;
    private final ObjectProperty<LocalDate> bookingDate;
    private final StringProperty status;
    private final IntegerProperty paxCount;

    public Booking(int bookingId, int employeeId, int clientId, String clientName, int packageId, String packageName, StringProperty destination, LocalDate bookingDate, String status, int paxCount) {
        this.bookingId = new SimpleIntegerProperty(this, "bookingId", bookingId);
        this.employeeId = new SimpleIntegerProperty(this, "employeeId", employeeId);
        this.clientId = new SimpleIntegerProperty(this, "clientId", clientId);
        this.clientName = new SimpleStringProperty(this, "clientName", clientName);
        this.packageName = new SimpleStringProperty(this, "packageName", packageName);
        this.packageId = new SimpleIntegerProperty(this, "packageId", packageId);
        this.destination = destination;
        this.bookingDate = new SimpleObjectProperty<>(this, "bookingDate", bookingDate);
        this.status = new SimpleStringProperty(this, "isConfirmed", status);
        this.paxCount = new SimpleIntegerProperty(this, "paxCount", paxCount);
    }
    // Getters and Setters omitted for brevity...
}
```

**`Payment.java`**
```java
package com.cht.travelmanagement.Models;

import java.math.BigDecimal;
import java.time.LocalDate;
import javafx.beans.property.*;

public class Payment {
    private final IntegerProperty paymentId;
    private final IntegerProperty bookingId;
    private final ObjectProperty<BigDecimal> amount;
    private final ObjectProperty<LocalDate> paymentDate;
    private final StringProperty method;
    private final StringProperty status;
    private final StringProperty referenceNumber;
    private final StringProperty clientName;
    private final StringProperty packageName;

    public Payment(int paymentId, int bookingId, BigDecimal amount, LocalDate paymentDate,
            String method, String status, String referenceNumber,
            String clientName, String packageName) {
        this.paymentId = new SimpleIntegerProperty(paymentId);
        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.amount = new SimpleObjectProperty<>(amount);
        this.paymentDate = new SimpleObjectProperty<>(paymentDate);
        this.method = new SimpleStringProperty(method);
        this.status = new SimpleStringProperty(status);
        this.referenceNumber = new SimpleStringProperty(referenceNumber);
        this.clientName = new SimpleStringProperty(clientName);
        this.packageName = new SimpleStringProperty(packageName);
    }
    // Getters and Setters omitted for brevity...
}
```

#### B. Core Configuration

**`App.java` (Main Entry Point)**
```java
package com.cht.travelmanagement;

import com.cht.travelmanagement.Models.DatabaseInitializer;
import com.cht.travelmanagement.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        launch(args);
    }
}
```

#### C. Data Access Object (Sample)

**`BookingRepositoryImpl.java`**
*(Refer to submitted `src/main/java/com/cht/travelmanagement/Models/Repository/Implementation/BookingRepositoryImpl.java` for full implementation)*

---

### 7. SQL Scripts

#### A. Data Definition Language (DDL)
*Defines the database structure.*

```sql
-- Table: employee
CREATE TABLE `employee` (
  `employeeId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` char(40) NOT NULL,
  `contactNumber` varchar(15) DEFAULT NULL,
  `isManager` bit(1) DEFAULT b'0',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`employeeId`),
  UNIQUE KEY `email` (`email`)
);

-- Table: client
CREATE TABLE `client` (
  `clientId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `address` varchar(50) NOT NULL,
  `contactNumber` varchar(15) NOT NULL,
  `customerType` enum('REGULAR','CORPORATE','VIP') DEFAULT 'REGULAR',
  `dateRegistered` date NOT NULL,
  PRIMARY KEY (`clientId`),
  UNIQUE KEY `email` (`email`)
);

-- Table: package
CREATE TABLE `package` (
  `PackageID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Description` text,
  `Destination` varchar(100) DEFAULT NULL,
  `Duration` int DEFAULT NULL,
  `MaxPax` int DEFAULT NULL,
  `Price` decimal(10,2) NOT NULL,
  `IsActive` tinyint(1) DEFAULT '1',
  `ImagePath` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PackageID`)
);

-- Table: booking
CREATE TABLE `booking` (
  `BookingID` int NOT NULL AUTO_INCREMENT,
  `EmployeeID` int DEFAULT NULL,
  `ClientID` int DEFAULT NULL,
  `PackageID` int DEFAULT NULL,
  `BookingDate` date DEFAULT NULL,
  `Status` varchar(50) DEFAULT NULL,
  `PaxCount` int DEFAULT NULL,
  PRIMARY KEY (`BookingID`),
  FOREIGN KEY (`EmployeeID`) REFERENCES `employee` (`employeeId`),
  FOREIGN KEY (`ClientID`) REFERENCES `client` (`clientId`),
  FOREIGN KEY (`PackageID`) REFERENCES `package` (`PackageID`)
);
```

#### B. Data Manipulation Language (DML)
*Key queries used in the application.*

**Login Verification:**
```sql
SELECT COUNT(1) FROM employee WHERE email = ? AND password = ? AND isActive = 1
```

**Get Recent Bookings:**
```sql
SELECT b.BookingID AS TripID, c.name AS CustomerName, p.Name AS PackageName, MIN(t.StartDate) AS StartDate, 
CASE WHEN MAX(t.EndDate) < CURRENT_DATE THEN 'Completed' ELSE 'Upcoming' END AS Status 
FROM booking b 
JOIN client c ON b.ClientID = c.clientId 
JOIN package p ON b.PackageID = p.PackageID 
WHERE b.Status = 'confirmed' 
GROUP BY b.BookingID 
ORDER BY StartDate ASC 
LIMIT 10
```

**Dashboard Statistics:**
```sql
SELECT 
    SUM(IF(TripEndDate < CURRENT_DATE, 1, 0)) AS CompletedTrips,
    SUM(IF(TripStartDate <= CURRENT_DATE AND TripEndDate >= CURRENT_DATE, 1, 0)) AS OngoingTrips,
    SUM(IF(TripStartDate > CURRENT_DATE, 1, 0)) AS UpcomingTrips
FROM ... (Calculated from Booking Dates)
```

---

### 8. JavaFX Source Code and User Interfaces

#### A. Login View
*The entry point of the application.*

[INSERT SCREENSHOT OF LOGIN WINDOW HERE]

**`Login-view.fxml`**
```xml
<StackPane prefHeight="700.0" prefWidth="900.0" styleClass="login-background" stylesheets="@../Styles/Login.css" xmlns="http://javafx.com/javafx/21" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.cht.travelmanagement.Controllers.LoginController">
    <!-- Login Card Container -->
    <VBox alignment="CENTER" maxHeight="580.0" maxWidth="420.0" spacing="20.0" styleClass="login-container">
        <!-- Logo Section -->
        <VBox alignment="CENTER" spacing="5" styleClass="logo-container">
            <ImageView fitHeight="100.0" fitWidth="200.0" pickOnBounds="true" preserveRatio="true" styleClass="logo-image">
                <image>
                    <Image url="@../Images/cht-logo.jpg" />
                </image>
            </ImageView>
        </VBox>
        <!-- Welcome Text -->
        <VBox alignment="CENTER" spacing="5">
            <Label styleClass="welcome-title" text="Welcome Back" />
            <Label styleClass="welcome-subtitle" text="Sign in to continue to your dashboard" />
        </VBox>
        <!-- Inputs and Buttons (Abbreviated) -->
        <VBox spacing="16">
            <TextField fx:id="usernameTextField" promptText="Enter your email or username" styleClass="login-text-field" />
            <PasswordField fx:id="passwordPasswordField" promptText="Enter your password" styleClass="login-password-field" />
            <Button fx:id="loginButton" text="Sign In" styleClass="login-button" />
        </VBox>
    </VBox>
</StackPane>
```

#### B. Admin Dashboard
*Provides an overview of system performance.*

[INSERT SCREENSHOT OF ADMIN DASHBOARD HERE]

**`AdminDashboard-view.fxml`**
```xml
<ScrollPane fitToWidth="true" stylesheets="@../../Styles/Admin/AdminDashboard.css" xmlns="http://javafx.com/javafx/25" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.cht.travelmanagement.Controllers.Admin.AdminDashboardController">
    <content>
        <VBox spacing="25.0" styleClass="dashboard-container">
            <!-- Header -->
            <HBox alignment="CENTER_LEFT" spacing="15.0">
                <Label styleClass="dashboard-title" text="Admin Dashboard" />
                <Button fx:id="refreshBtn" text="Refresh Data" />
            </HBox>
            <!-- Stats Cards Row -->
            <HBox alignment="CENTER" spacing="20.0" styleClass="stats-row">
                <VBox styleClass="stat-card,sales-card">
                    <Label fx:id="totalSalesLabel" styleClass="stat-value" text="₱0.00" />
                    <Label styleClass="stat-label" text="Total Sales" />
                </VBox>
                <!-- Other cards... -->
            </HBox>
            <!-- Charts and Tables... -->
        </VBox>
    </content>
</ScrollPane>
```

#### C. Booking Wizard (Step 1 - Client Selection)
*First step in creating a new booking.*

[INSERT SCREENSHOT OF BOOKING WIZARD STEP 1 HERE]

**`BookingStep1-view.fxml`**
```xml
<AnchorPane stylesheets="@../../../Styles/User/BookingStep1.css" style="-fx-background-color: #F4F7FE;" xmlns="http://javafx.com/javafx/25" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.cht.travelmanagement.Controllers.User.BookingWizard.BookingStep1Controller">
   <children>
      <ScrollPane fitToWidth="true" fitToHeight="true" style="-fx-background-color: transparent;">
         <content>
            <VBox spacing="25.0" styleClass="wizard-container">
                <!-- Header -->
                <VBox spacing="5.0">
                    <Label styleClass="header-title" text="Customer Information" />
                    <Label styleClass="header-subtitle" text="Search for an existing customer or enter new customer details." />
                </VBox>
                <!-- Search Section -->
                <VBox spacing="10.0">
                    <Label styleClass="section-label" text="Existing Customer Lookup" />
                    <TextField fx:id="search" promptText="Search by Name, Email or ID..." styleClass="search-field" />
                    <ListView fx:id="search_results_list" visible="false" />
                </VBox>
                <!-- Form Fields (Abbreviated) -->
                <GridPane hgap="20.0" vgap="20.0">
                     <TextField fx:id="fname_fld" promptText="Enter full name" />
                     <TextField fx:id="email_fld" promptText="email@example.com" />
                     <ChoiceBox fx:id="destination_box" />
                </GridPane>
            </VBox>
         </content>
      </ScrollPane>
   </children>
</AnchorPane>
```

---
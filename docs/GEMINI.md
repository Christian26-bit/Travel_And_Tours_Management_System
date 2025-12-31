# Travel Management System

## Project Overview
This is a JavaFX-based desktop application designed for travel management. It features a Graphical User Interface (GUI) built with FXML and CSS, and it connects to a MySQL database for data persistence. The application appears to support different user roles, such as Administrators and Standard Users, as suggested by the directory structure.

## Architecture
The project follows the standard **Model-View-Controller (MVC)** architectural pattern:
*   **Model:** Handles data logic and database interactions (`src/main/java/com/cht/travelmanagement/Models`).
*   **View:** Defines the UI structure using FXML files (`src/main/resources/Views`) and styling with CSS (`src/main/resources/Styles`).
*   **Controller:** Manages the logic between the Model and View (`src/main/java/com/cht/travelmanagement/Controllers`).

## Technology Stack
*   **Language:** Java 21
*   **GUI Framework:** JavaFX 21 (Controls, FXML)
*   **Build Tool:** Maven
*   **Database:** MySQL (Connector/J 9.5.0)
*   **Testing:** JUnit 5

## Prerequisites
1.  **Java JDK 21:** Ensure Java 21 is installed and `JAVA_HOME` is set.
2.  **Maven:** Required for building the project.
3.  **MySQL Server:** A local MySQL instance is required.

## Configuration & Database Setup
The database configuration is currently hardcoded in `src/main/java/com/cht/travelmanagement/Models/DatabaseDriver.java`.

**Default Credentials:**
*   **Host:** `localhost:3306`
*   **Database Name:** `cht_updated`
*   **User:** `root`
*   **Password:** `password`

**Setup:**
1.  Ensure your MySQL server is running.
2.  Create a database named `cht_updated`.
3.  Modify `DatabaseDriver.java` if your credentials differ.

## Build and Run

### Running via Maven (Recommended for Development)
To compile and run the application directly:
```bash
mvn clean javafx:run
```

### Building a Runnable JAR
To package the application into a standalone JAR (using the Maven Shade Plugin):
```bash
mvn clean package
```
This will generate a shaded JAR in the `target/` directory, which can be run with:
```bash
java -jar target/travelmanagement-1.0-SNAPSHOT.jar
```
*(Note: Ensure you use the shaded/executable JAR, usually the larger one if multiple are generated).*

## Key Directory Structure
*   `src/main/java/com/cht/travelmanagement/`
    *   `App.java`: Main JavaFX entry point.
    *   `Launcher.java`: Wrapper main class for JAR execution.
    *   `Controllers/`: Java classes handling UI logic (Admin/User subpackages).
    *   `Models/`: Data classes and `DatabaseDriver`.
    *   `View/`: View factories and UI helpers.
*   `src/main/resources/`
    *   `Views/`: FXML layout files.
    *   `Styles/`: CSS stylesheets.
    *   `Images/`: Application assets.

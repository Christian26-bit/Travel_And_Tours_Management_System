module com.cht.travelmanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cht.travelmanagement to javafx.fxml;
    exports com.cht.travelmanagement;
}
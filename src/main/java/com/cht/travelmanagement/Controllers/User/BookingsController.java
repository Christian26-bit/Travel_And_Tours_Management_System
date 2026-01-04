package com.cht.travelmanagement.Controllers.User;

import com.cht.travelmanagement.Models.Booking;
import com.cht.travelmanagement.Models.Model;
import com.cht.travelmanagement.View.UserMenuOption;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookingsController implements Initializable {

    @FXML
    private TableView<Booking> booking_table;

    @FXML
    private TableColumn<Booking, String> bookingId_col;

    @FXML
    private TableColumn<Booking, String> client_col;

    @FXML
    private TableColumn<Booking, String> destination_col;

    @FXML
    private TableColumn<Booking, String> endDate_col;

    @FXML
    private Button newBooking_btn;

    @FXML
    private TableColumn<Booking, String> package_col;

    @FXML
    private TextField search_fld;

    @FXML
    private TableColumn<Booking, String> startDate_col;

    @FXML
    private TableColumn<Booking, String> status_col;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadAllBookings();
    }
    private void LoadAllBookings() {
        ObservableList<Booking> recentBookings = Model.getInstance().getAllBookings();
        newBooking_btn.setOnAction(event -> {onNewBookingButtonClicked();});

        loadBookings(recentBookings , booking_table);
    }

    private void onNewBookingButtonClicked() {
        Model.getInstance().getUserViewFactory().getUserSelectedMenuItem().set(UserMenuOption.NEW_BOOKING);
    }

    /**
     * Load bookings into the ListView with custom cells.
     */
    public void loadBookings(ObservableList<Booking> bookings, TableView<Booking> booking_table) {
        booking_table.setItems(bookings);

        bookingId_col.setCellValueFactory(cellData -> cellData.getValue().bookingIdProperty().asString());
        client_col.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
        package_col.setCellValueFactory(cellData -> cellData.getValue().packageNameProperty());
        destination_col.setCellValueFactory(cellData -> cellData.getValue().destinationProperty());
        startDate_col.setCellValueFactory(cellData -> cellData.getValue().bookingDateProperty().asString());
        endDate_col.setCellValueFactory(cellData -> cellData.getValue().bookingDateProperty().asString());
        status_col.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

    }

    /**
     *
     */
}

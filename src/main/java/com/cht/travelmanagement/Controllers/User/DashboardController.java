package com.cht.travelmanagement.Controllers.User;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import com.cht.travelmanagement.Models.Booking;
import com.cht.travelmanagement.Models.Model;
import com.cht.travelmanagement.View.UserMenuOption;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class DashboardController implements Initializable {
    
    @FXML
    public ListView<Booking> bookingList;

    @FXML
    public Label completedTrips_lbl;

    @FXML
    public Button newBooking_btn;

    @FXML
    public Label ongoingTrips_lbl;

    @FXML
    public Label totalCustomer_lbl;

    @FXML
    public Label upcomingTrips_lbl;

    @FXML
    public Button viewAll_btn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newBooking_btn.setOnAction(event -> onNewBookingButtonClicked());
        viewAll_btn.setOnAction(event -> onViewAllButtonClicked());

        getDashboardData();
    }

    private void onNewBookingButtonClicked() {
        Model.getInstance().getUserViewFactory().getUserSelectedMenuItem().set(UserMenuOption.NEW_BOOKING);
    }
    private void onViewAllButtonClicked() {
        Model.getInstance().getUserViewFactory().getUserSelectedMenuItem().set(UserMenuOption.BOOKINGS);
    }

    private void getDashboardData() {
        int[] dashboardData = Model.getInstance().getDashboardData();
        totalCustomer_lbl.setText(String.valueOf(dashboardData[0]));
        ongoingTrips_lbl.setText(String.valueOf(dashboardData[1]));
        upcomingTrips_lbl.setText(String.valueOf(dashboardData[2]));
        completedTrips_lbl.setText(String.valueOf(dashboardData[3]));
        LoadRecentBookings();
        
    }

    private void LoadRecentBookings() {
        ObservableList<Booking> recentBookings = Model.getInstance().getRecentBookings();
        BookingsController bookingsController = new BookingsController();
        bookingsController.loadBookings(recentBookings, bookingList);
       
    }
}

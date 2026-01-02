package com.cht.travelmanagement.Controllers.User;


import com.cht.travelmanagement.Models.Booking;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookingItemController {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblId;

    @FXML
    private Label lblInitials;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPackage;

    @FXML
    private Label lblStatus;

    public void setData(Booking booking) {
        lblId.setText("Booking #" + booking.getBookingId());
        lblName.setText(booking.getClientName());
        lblPackage.setText(booking.getPackageName());
        lblDate.setText(booking.getBookingDate().toString());
        lblStatus.setText(booking.getStatus());
        if (booking.getPackageName() != null && !booking.getPackageName().isEmpty()) {
            lblInitials.setText(booking.getPackageName().substring(0, 1).toUpperCase());
        } else {
            lblInitials.setText("N/A");
        }
        lblLocation.setText("Location: " + booking.getDestination());
    }



}

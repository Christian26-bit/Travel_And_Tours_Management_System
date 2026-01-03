package com.cht.travelmanagement.Controllers.User;

import com.cht.travelmanagement.Models.Model;
import com.cht.travelmanagement.View.BookingButtons;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class NewBookingController implements Initializable {

    @FXML
    public BorderPane newBooking_parent;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBookingStep(Model.getInstance().getUserViewFactory().getBookingStep().get());

        Model.getInstance().getUserViewFactory().getBookingStep().addListener((observable, oldValue, newValue) -> {
            loadBookingStep(newValue.intValue());
        });
    }

    public void loadBookingStep(int step) {
        switch (step){
            case 1 -> newBooking_parent.setCenter(Model.getInstance().getUserViewFactory().getBookingStep1View());
            case 2 -> newBooking_parent.setCenter(Model.getInstance().getUserViewFactory().getBookingStep2View());
            case 3 -> newBooking_parent.setCenter(Model.getInstance().getUserViewFactory().getBookingStep3View());
            case 4 -> newBooking_parent.setCenter(Model.getInstance().getUserViewFactory().getBookingStep4View());
            case 5 -> newBooking_parent.setCenter(Model.getInstance().getUserViewFactory().getBookingStep5View());
            case 6 -> newBooking_parent.setCenter(Model.getInstance().getUserViewFactory().getBookingStep6View());
            default ->  newBooking_parent.setCenter(null);
        }
    }


}

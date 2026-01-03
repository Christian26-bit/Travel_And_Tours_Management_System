package com.cht.travelmanagement.Controllers.User.BookingWizard;

import java.net.URL;
import java.util.ResourceBundle;

import com.cht.travelmanagement.Controllers.User.BookingsController;
import com.cht.travelmanagement.Controllers.User.NewBookingController;
import com.cht.travelmanagement.Models.Model;
import com.cht.travelmanagement.View.BookingButtons;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class BookingNavigationController implements Initializable {

    public Button back_btn;
    public Label status_lbl;
    public Button next_btn;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateLabel(Model.getInstance().getUserViewFactory().getBookingStep().get());
        back_btn.setOnMouseClicked(event -> onBackBtnClicked());
        next_btn.setOnMouseClicked(event -> onNextBtnClicked() );


    }


    public void onBackBtnClicked() {
        int currentStep = Model.getInstance().getUserViewFactory().getBookingStep().get();

        if  (currentStep > 1) {
            currentStep--;
            Model.getInstance().getUserViewFactory().getBookingStep().set(currentStep);
            updateLabel(currentStep);
        }




    }

    public void onNextBtnClicked() {
        int currentStep = Model.getInstance().getUserViewFactory().getBookingStep().get();

        if  (currentStep < 6) {
            currentStep++;
            Model.getInstance().getUserViewFactory().getBookingStep().set(currentStep);
            updateLabel(currentStep);
        }
    }

    private void updateLabel(int currentStep) {
        status_lbl.setText( "Step "+ currentStep +" of 6");
    }





}

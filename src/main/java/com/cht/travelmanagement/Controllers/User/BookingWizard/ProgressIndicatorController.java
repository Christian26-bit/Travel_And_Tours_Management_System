package com.cht.travelmanagement.Controllers.User.BookingWizard;

import java.net.URL;
import java.util.ResourceBundle;

import com.cht.travelmanagement.Models.Model;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class ProgressIndicatorController implements Initializable {

    @FXML
    public VBox step1_indicator;
    @FXML
    public VBox step2_indicator;
    @FXML
    public VBox step3_indicator;
    @FXML
    public VBox step4_indicator;
    @FXML
    public VBox step5_indicator;
    @FXML
    public VBox step6_indicator;

    @FXML
    public Region line1;
    @FXML
    public Region line2;
    @FXML
    public Region line3;
    @FXML
    public Region line4;
    @FXML
    public Region line5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int currentStep = Model.getInstance().getUserViewFactory().getBookingStep().get();
        updateProgress(currentStep);

        Model.getInstance().getUserViewFactory().getBookingStep().addListener((obs, oldVal, newVal) -> {
            updateProgress(newVal.intValue());
        });
    }

    private void updateProgress(int currentStep) {
        updateSingleStep(step1_indicator, 1, currentStep);
        updateSingleStep(step2_indicator, 2, currentStep);
        updateSingleStep(step3_indicator, 3, currentStep);
        updateSingleStep(step4_indicator, 4, currentStep);
        updateSingleStep(step5_indicator, 5, currentStep);
        updateSingleStep(step6_indicator, 6, currentStep);

        // Update progress lines
        updateProgressLine(line1, 1, currentStep);
        updateProgressLine(line2, 2, currentStep);
        updateProgressLine(line3, 3, currentStep);
        updateProgressLine(line4, 4, currentStep);
        updateProgressLine(line5, 5, currentStep);
    }

    private void updateSingleStep(VBox indicator, int stepNumber, int currentStep) {
        StackPane stack = (StackPane) indicator.getChildren().get(0);
        Circle outerCircle = (Circle) stack.getChildren().get(0);
        Circle innerCircle = (Circle) stack.getChildren().get(1);
        Label numberLabel = (Label) stack.getChildren().get(2);
        Label textLabel = (Label) indicator.getChildren().get(1);

        // Clear existing style classes
        outerCircle.getStyleClass().removeAll("step-circle-active", "step-circle-inactive", "step-circle-completed");
        innerCircle.getStyleClass().removeAll("step-circle-inner", "step-circle-inner-inactive", "step-circle-inner-completed");
        numberLabel.getStyleClass().removeAll("step-number", "step-number-inactive");
        textLabel.getStyleClass().removeAll("step-label-active", "step-label-inactive", "step-label-completed");

        if (stepNumber < currentStep) {
            // Completed step
            outerCircle.getStyleClass().add("step-circle-completed");
            innerCircle.getStyleClass().add("step-circle-inner-completed");
            numberLabel.getStyleClass().add("step-number");
            numberLabel.setText("✓");
            textLabel.getStyleClass().add("step-label-completed");
        } else if (stepNumber == currentStep) {
            // Active step
            outerCircle.getStyleClass().add("step-circle-active");
            innerCircle.getStyleClass().add("step-circle-inner");
            numberLabel.getStyleClass().add("step-number");
            numberLabel.setText(String.valueOf(stepNumber));
            textLabel.getStyleClass().add("step-label-active");
        } else {
            // Inactive step
            outerCircle.getStyleClass().add("step-circle-inactive");
            innerCircle.getStyleClass().add("step-circle-inner-inactive");
            numberLabel.getStyleClass().add("step-number-inactive");
            numberLabel.setText(String.valueOf(stepNumber));
            textLabel.getStyleClass().add("step-label-inactive");
        }
    }

    private void updateProgressLine(Region line, int lineAfterStep, int currentStep) {
        if (line == null) {
            return;
        }

        line.getStyleClass().removeAll("progress-line-active", "progress-line-inactive");

        if (lineAfterStep < currentStep) {
            line.getStyleClass().add("progress-line-active");
        } else {
            line.getStyleClass().add("progress-line-inactive");
        }
    }
}

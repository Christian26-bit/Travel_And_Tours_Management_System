package com.cht.travelmanagement.Controllers.User.BookingWizard;

import java.net.URL;
import java.util.ResourceBundle;

import com.cht.travelmanagement.Models.BookingData;
import com.cht.travelmanagement.Models.Model;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class BookingSummaryController implements Initializable {

    @FXML
    private Label complete_percent_lbl;
    @FXML
    private Label customer_lbl;
    @FXML
    private Label package_lbl;
    @FXML
    private Label hotel_lbl;
    @FXML
    private Label transport_lbl;
    @FXML
    private Label total_cost_lbl;
    @FXML
    private Label cost_hint_txt;
    @FXML
    private Label completion_count_lbl;
    @FXML
    private Circle progress_ring;

    private BookingData bookingData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingData = Model.getInstance().getBookingData();

        setupBindings();
        updateSummary();

        // Listen for step changes to update summary
        Model.getInstance().getUserViewFactory().getBookingStep().addListener((obs, oldVal, newVal) -> {
            updateSummary();
        });
    }

    private void setupBindings() {
        // Bind customer name
        bookingData.clientNameProperty().addListener((obs, oldVal, newVal) -> updateCustomerSection());
        bookingData.paxCountProperty().addListener((obs, oldVal, newVal) -> updateCustomerSection());

        // Bind package selection
        bookingData.selectedPackageNameProperty().addListener((obs, oldVal, newVal) -> updatePackageSection());
        bookingData.packagePriceProperty().addListener((obs, oldVal, newVal) -> {
            updatePackageSection();
            updateTotalCost();
        });

        // Bind hotel selection
        bookingData.selectedHotelNameProperty().addListener((obs, oldVal, newVal) -> updateHotelSection());
        bookingData.hotelPriceProperty().addListener((obs, oldVal, newVal) -> {
            updateHotelSection();
            updateTotalCost();
        });

        // Bind vehicle selection
        bookingData.selectedVehicleNameProperty().addListener((obs, oldVal, newVal) -> updateTransportSection());
        bookingData.vehiclePriceProperty().addListener((obs, oldVal, newVal) -> {
            updateTransportSection();
            updateTotalCost();
        });

        // Bind add-ons
        bookingData.includeBreakfastProperty().addListener((obs, oldVal, newVal) -> updateTotalCost());
        bookingData.includeInsuranceProperty().addListener((obs, oldVal, newVal) -> updateTotalCost());
        bookingData.includeGuideProperty().addListener((obs, oldVal, newVal) -> updateTotalCost());
        bookingData.includePickupProperty().addListener((obs, oldVal, newVal) -> updateTotalCost());
    }

    private void updateSummary() {
        updateCustomerSection();
        updatePackageSection();
        updateHotelSection();
        updateTransportSection();
        updateTotalCost();
        updateProgress();
    }

    private void updateCustomerSection() {
        if (customer_lbl != null) {
            if (!bookingData.getClientName().isEmpty()) {
                customer_lbl.setText(bookingData.getClientName() + " (" + bookingData.getPaxCount() + " pax)");
                customer_lbl.getStyleClass().removeAll("summary-item-value");
                customer_lbl.getStyleClass().add("summary-item-value-complete");
            } else {
                customer_lbl.setText("Not set");
                customer_lbl.getStyleClass().removeAll("summary-item-value-complete");
                customer_lbl.getStyleClass().add("summary-item-value");
            }
        }
    }

    private void updatePackageSection() {
        if (package_lbl != null) {
            if (bookingData.getSelectedPackageId() > 0) {
                package_lbl.setText(bookingData.getSelectedPackageName());
                package_lbl.getStyleClass().removeAll("summary-item-value");
                package_lbl.getStyleClass().add("summary-item-value-complete");
            } else {
                package_lbl.setText("Not selected");
                package_lbl.getStyleClass().removeAll("summary-item-value-complete");
                package_lbl.getStyleClass().add("summary-item-value");
            }
        }
    }

    private void updateHotelSection() {
        if (hotel_lbl != null) {
            if (bookingData.getSelectedHotelId() > 0) {
                hotel_lbl.setText(bookingData.getSelectedHotelName());
                hotel_lbl.getStyleClass().removeAll("summary-item-value");
                hotel_lbl.getStyleClass().add("summary-item-value-complete");
            } else {
                hotel_lbl.setText("Not selected");
                hotel_lbl.getStyleClass().removeAll("summary-item-value-complete");
                hotel_lbl.getStyleClass().add("summary-item-value");
            }
        }
    }

    private void updateTransportSection() {
        if (transport_lbl != null) {
            if (bookingData.getSelectedVehicleId() > 0) {
                transport_lbl.setText(bookingData.getVehicleType());
                transport_lbl.getStyleClass().removeAll("summary-item-value");
                transport_lbl.getStyleClass().add("summary-item-value-complete");
            } else {
                transport_lbl.setText("Not selected");
                transport_lbl.getStyleClass().removeAll("summary-item-value-complete");
                transport_lbl.getStyleClass().add("summary-item-value");
            }
        }
    }

    private void updateTotalCost() {
        if (total_cost_lbl != null) {
            int total = bookingData.calculateTotalPrice();
            total_cost_lbl.setText("₱" + String.format("%,d", total));

            if (cost_hint_txt != null) {
                if (total > 0) {
                    cost_hint_txt.setText(getBreakdown());
                } else {
                    cost_hint_txt.setText("Add items to see total");
                }
            }
        }
    }

    private String getBreakdown() {
        StringBuilder sb = new StringBuilder();

        if (bookingData.getPackagePrice() > 0) {
            sb.append("Package: ₱").append(String.format("%,d", bookingData.getPackagePrice() * bookingData.getPaxCount())).append("\n");
        }

        int addons = bookingData.getAddonsTotal();
        if (addons > 0) {
            sb.append("Add-ons: ₱").append(String.format("%,d", addons)).append("\n");
        }

        if (bookingData.getHotelPrice() > 0) {
            sb.append("Hotel: ₱").append(String.format("%,d", bookingData.getHotelPrice())).append("\n");
        }

        if (bookingData.getVehiclePrice() > 0) {
            sb.append("Transport: ₱").append(String.format("%,d", bookingData.getVehiclePrice()));
        }

        return sb.length() > 0 ? sb.toString().trim() : "Add items to see total";
    }

    private void updateProgress() {
        int completedCount = bookingData.getCompletedStepsCount();
        int totalSteps = 5; // Customer, Package, Add-ons (always complete), Hotel, Transport

        double progress = (double) completedCount / totalSteps;
        int percentage = (int) (progress * 100);

        if (complete_percent_lbl != null) {
            complete_percent_lbl.setText(percentage + "% Complete");
        }

        if (completion_count_lbl != null) {
            completion_count_lbl.setText(completedCount + "/" + totalSteps);
        }

        // Update progress ring (stroke-dasharray based on progress)
        if (progress_ring != null) {
            double circumference = 2 * Math.PI * 35; // radius = 35
            double dashLength = circumference * progress;
            double gapLength = circumference - dashLength;
            progress_ring.setStyle(String.format(
                    "-fx-fill: transparent; -fx-stroke: %s; -fx-stroke-width: 6; -fx-stroke-dash-array: %.2f %.2f; -fx-stroke-line-cap: round; -fx-rotate: -90;",
                    progress >= 1.0 ? "#05CD99" : "#4318FF",
                    dashLength,
                    gapLength
            ));
        }
    }
}

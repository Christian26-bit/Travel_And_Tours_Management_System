package com.cht.travelmanagement.View;

import java.net.URL;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Utility class for displaying styled alerts and modals consistent with the
 * application theme.
 */
public class AlertUtility {

    /**
     * Show a prompt dialog for text input.
     *
     * @return the user's input, or null if cancelled.
     */
    public static String promptInput(String title, String header, String defaultValue) {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText("");
        // Style the dialog using its DialogPane
        DialogPane dialogPane = dialog.getDialogPane();
        URL cssResource = AlertUtility.class.getResource(CSS_PATH);
        if (cssResource != null) {
            dialogPane.getStylesheets().add(cssResource.toExternalForm());
        }
        dialogPane.getStyleClass().add("modal-dialog");
        javafx.scene.Node headerNode = dialogPane.lookup(".header-panel");
        if (headerNode != null) {
            headerNode.getStyleClass().add("modal-header-info");
        } else {
            dialogPane.getStyleClass().add("modal-header-info-pane");
        }
        java.util.Optional<String> result = dialog.showAndWait();
        return result.filter(s -> !s.trim().isEmpty()).orElse(null);
    }

    private static final String CSS_PATH = "/Styles/Modals.css";

    /**
     * Show a simple information alert.
     */
    public static void showInfo(String title, String header, String content) {
        showAlert(Alert.AlertType.INFORMATION, title, header, content);
    }

    /**
     * Show a success alert (using Information type but styled for success).
     */
    public static void showSuccess(String title, String header, String content) {
        Alert alert = createAlert(Alert.AlertType.INFORMATION, title, header, content);
        styleAlert(alert, "modal-header-success");
        alert.showAndWait();
    }

    /**
     * Show a warning alert.
     */
    public static void showWarning(String title, String header, String content) {
        Alert alert = createAlert(Alert.AlertType.WARNING, title, header, content);
        styleAlert(alert, "modal-header-warning");
        alert.showAndWait();
    }

    /**
     * Show an error alert.
     */
    public static void showError(String title, String header, String content) {
        Alert alert = createAlert(Alert.AlertType.ERROR, title, header, content);
        styleAlert(alert, "modal-header-danger");
        alert.showAndWait();
    }

    /**
     * Show an error alert with a detailed exception stack trace or long
     * message.
     */
    public static void showErrorDetails(String title, String header, String content, String details) {
        Alert alert = createAlert(Alert.AlertType.ERROR, title, header, content);
        styleAlert(alert, "modal-header-danger");

        Label label = new Label("Exception Stacktrace:");
        TextArea textArea = new TextArea(details);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    /**
     * Show a confirmation dialog.
     *
     * @return true if the user clicked OK/Yes, false otherwise.
     */
    public static boolean showConfirmation(String title, String header, String content) {
        Alert alert = createAlert(Alert.AlertType.CONFIRMATION, title, header, content);
        styleAlert(alert, "modal-header-info");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private static void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = createAlert(type, title, header, content);
        styleAlert(alert, getHeaderStyleClass(type));
        alert.showAndWait();
    }

    private static Alert createAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    private static void styleAlert(Alert alert, String headerClass) {
        DialogPane dialogPane = alert.getDialogPane();

        // Add CSS
        URL cssResource = AlertUtility.class.getResource(CSS_PATH);
        if (cssResource != null) {
            dialogPane.getStylesheets().add(cssResource.toExternalForm());
        } else {
            System.err.println("AlertUtility: Could not find CSS file at " + CSS_PATH);
        }

        dialogPane.getStyleClass().add("modal-dialog");

        // Apply styling to the header panel
        // We need to access the header-panel region. It usually exists if header text is set.
        javafx.scene.Node header = dialogPane.lookup(".header-panel");
        if (header != null) {
            header.getStyleClass().add(headerClass);
        } else {
            // Sometimes lookup fails if not yet shown, but for Alert it usually works. 
            // If it fails, we can add the class to the dialogPane and handle it in CSS via hierarchy
            dialogPane.getStyleClass().add(headerClass + "-pane");
        }

        // Optional: Style the graphic if it exists (the icon)
        javafx.scene.Node graphic = dialogPane.getGraphic();
        if (graphic != null) {
            graphic.getStyleClass().add("dialog-icon");
        }
    }

    private static String getHeaderStyleClass(Alert.AlertType type) {
        return switch (type) {
            case INFORMATION, CONFIRMATION ->
                "modal-header-info";
            case WARNING ->
                "modal-header-warning";
            case ERROR ->
                "modal-header-danger";
            default ->
                "modal-header";
        };
    }
}

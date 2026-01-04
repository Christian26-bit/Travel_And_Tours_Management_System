package com.cht.travelmanagement.Controllers.User;

import java.net.URL;
import java.util.ResourceBundle;

import com.cht.travelmanagement.Models.Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClientsController implements Initializable {

    @FXML
    private TableView<Client> clients_table;

    @FXML
    private TableColumn<?, ?> clientName_col;

    @FXML
    private TableColumn<?, ?> contactNum_col;

    @FXML
    private TextField customerSearch_fld;

    @FXML
    private TableColumn<?, ?> destination_col;

    @FXML
    private TableColumn<?, ?> email_col;

    @FXML
    private TableColumn<?, ?> tripDates_col;

    @FXML
    private TableColumn<?, ?> tripStatus_col;

    @FXML
    private TableColumn<?, ?> actions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}

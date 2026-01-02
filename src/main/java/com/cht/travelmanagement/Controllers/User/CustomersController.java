package com.cht.travelmanagement.Controllers.User;

import com.cht.travelmanagement.Models.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    public TextField customerSearch_fld;
    public ListView<Client> customerListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

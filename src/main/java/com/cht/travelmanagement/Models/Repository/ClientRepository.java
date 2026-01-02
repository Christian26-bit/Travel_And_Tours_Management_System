package com.cht.travelmanagement.Models.Repository;

import com.cht.travelmanagement.Models.Client;
import javafx.collections.ObservableList;

public interface ClientRepository {
    public ObservableList<Client> getClients();
}

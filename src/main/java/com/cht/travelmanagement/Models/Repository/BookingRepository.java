package com.cht.travelmanagement.Models.Repository;

import java.util.Observable;

import com.cht.travelmanagement.Models.Booking;

import javafx.collections.ObservableList;

public interface BookingRepository {
    public int[] getDashboardData(int clientCount);
    public ObservableList<Booking> getRecentBookings();
    public ObservableList<Booking> getAllBookings();


}

package com.cht.travelmanagement.Models.Repository;

import com.cht.travelmanagement.Models.TourPackage;
import javafx.collections.ObservableList;

public interface TourPackageRepository {
    public ObservableList<TourPackage> getTourPackages();

}

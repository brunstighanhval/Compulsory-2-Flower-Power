package GUI.Model;

import BE.Location;
import BLL.LocationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LocationModel {
    private LocationManager eventManager;
    public Location createdLocation;
    private ObservableList<Location> observableLocations;

    public LocationModel() throws Exception {
        eventManager = new LocationManager();
        observableLocations = FXCollections.observableArrayList();
        observableLocations.addAll(eventManager.getAllLocations());
    }

    public void createLocation(String name, String address, int zipCode) throws Exception {
        createdLocation = eventManager.createLocation(name, address, zipCode);
        observableLocations.add(createdLocation);
        observableLocations.clear();
        observableLocations.addAll(eventManager.getAllLocations());
    }

    public ObservableList<Location> getObservableLocations(){
        return observableLocations;
    }
    }



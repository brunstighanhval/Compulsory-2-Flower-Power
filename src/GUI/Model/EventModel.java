package GUI.Model;

import BE.Location;
import BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;

public class EventModel {

    private EventManager eventManager;
    public Location createdLocation;

    private ObservableList<Location> observableLocations;

    public EventModel() throws Exception {
        eventManager = new EventManager();
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



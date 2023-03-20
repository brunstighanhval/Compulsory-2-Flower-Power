package BLL;

import BE.Location;
import DAL.db.EventDAO;
import DAL.db.IEvent;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class EventManager {

    private IEvent eventDAO;

    public EventManager() throws IOException {eventDAO = new EventDAO();}

    public Location createLocation(String name, String address, int zipCode) throws Exception {
        return eventDAO.createLocation(name,address,zipCode);
    }

    public List<Location> getAllLocations() throws Exception {
        return eventDAO.getAllLocations();

    }
}

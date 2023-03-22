package BLL;

import BE.Location;
import DAL.db.ILocation;
import DAL.db.LocationDAO;

import java.io.IOException;
import java.util.List;

public class LocationManager {

    private ILocation eventDAO;

    public LocationManager() throws IOException {eventDAO = new LocationDAO();}

    public Location createLocation(String name, String address, int zipCode) throws Exception {
        return eventDAO.createLocation(name,address,zipCode);
    }

    public List<Location> getAllLocations() throws Exception {
        return eventDAO.getAllLocations();

    }
}

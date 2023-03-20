package DAL.db;

import BE.Location;

import java.util.List;

public interface IEvent {

    Location createLocation(String name, String address, int zipCode) throws Exception;

    List<Location> getAllLocations() throws Exception;
}

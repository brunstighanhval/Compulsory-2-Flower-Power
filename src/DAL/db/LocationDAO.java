package DAL.db;

import BE.Location;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO implements ILocation {

    private DatabaseConnector databaseConnector;

    public LocationDAO() throws IOException {DatabaseConnector.getInstance();}

    @Override
    public Location createLocation(String name, String address, int zipCode) throws Exception {

        String sql = "INSERT INTO Location (Name, Address, Zip_Code) VALUES (?,?,?);";
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Binding parameters.
            stmt.setString(1,name);
            stmt.setString(2, address);
            stmt.setInt(3, zipCode);

            //Run the SQL statement.
            stmt.executeUpdate();

            //Get the ID from the database
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }
            //Create the movie.
            Location location = new Location(id,name,address,zipCode);
            return location;

        } catch (SQLException ex){
            ex.printStackTrace();

            throw new Exception("Could not create location", ex);
        }
    }

    @Override
    public List<Location> getAllLocations() throws Exception {
        ArrayList<Location> allLocations = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            //SQL statement.
            String sql = "SELECT * FROM Location;";
            Statement stmt = conn.createStatement();
            //Run the statement.
            ResultSet rs = stmt.executeQuery(sql);
            //Keep adding as long as there is a next movie.
            while (rs.next()) {
                int id = rs.getInt("Venue_ID");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                int zipCode = rs.getInt("Zip_Code");

                Location location = new Location(id, name, address, zipCode);
                allLocations.add(location);
            }
            return allLocations;

        } catch (SQLException | IOException ex){
            ex.printStackTrace();
            throw new Exception("Could not get locations from database", ex);
        }
    }
}

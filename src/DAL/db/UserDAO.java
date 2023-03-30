package DAL.db;

import BE.EventKoordinator;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private DatabaseConnector databaseConnector;

    public UserDAO() throws IOException {
        databaseConnector = DatabaseConnector.getInstance();
    }

    public List<EventKoordinator> loadAllUsers() throws Exception{
        ArrayList<EventKoordinator> allEvk = new ArrayList<>();
        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM Event_Koordinator;";

            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {
                //Map DB row to Song object
                int id = rs.getInt("EVK_ID");
                String last_name = rs.getString("Last_Name");
                String first_name = rs.getString("First_Name");
                String user_name = rs.getString("User_Name");
                String password = rs.getString("Password");

                EventKoordinator EvK = new EventKoordinator(id, last_name, first_name, user_name, password);

                allEvk.add(EvK);
            }
            return allEvk;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get EventKoordinator from database", ex);
        }
    }

    public boolean validate(String email, String password) throws Exception{
        String sql = "SELECT * FROM Event_Koordinator WHERE User_Name = ? and Password = ?";
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to validate", e);
        }
        return false;
    }
}

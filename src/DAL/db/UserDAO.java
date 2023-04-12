package DAL.db;

import BE.Ticket;
import BE.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDataAccess {

    private DatabaseConnector databaseConnector;

    public UserDAO() throws IOException {
        databaseConnector = DatabaseConnector.getInstance();
    }

    public List<User> loadUser(String username) throws Exception {
        ArrayList<User> allEvk = new ArrayList<>();
        try (Connection conn = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Event_Koordinator WHERE User_Name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            // Loop through rows from the database result set
            while (rs.next()) {
                //Map DB row to Song object
                int id = rs.getInt("Id");
                String last_name = rs.getString("Last_Name");
                String first_name = rs.getString("First_Name");
                String user_name = rs.getString("User_Name");
                String password = rs.getString("Password");
                int role = rs.getInt("Role");

                User user = new User(id, last_name, first_name, user_name, password, role);

                allEvk.add(user);
            }
            return allEvk;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not get EventKoordinator from database", ex);
        }
    }

    public boolean validate(String username) throws Exception {
        String sql = "SELECT * FROM Event_Koordinator WHERE User_Name = ?";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to validate", e);
        }
        return false;
    }

    public List<User> readEvK() throws Exception {
        ArrayList<User> Evks = new ArrayList<>();
        String sql = "SELECT * FROM Event_Koordinator WHERE Role = 2";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Map DB row to Song object
                int id = rs.getInt("Id");
                String last_name = rs.getString("Last_Name");
                String first_name = rs.getString("First_Name");
                String user_name = rs.getString("User_Name");
                String password = rs.getString("Password");
                int role = rs.getInt("Role");

                User user = new User(id, last_name, first_name, user_name, password, role);

                Evks.add(user);
            }
            return Evks;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not get EventKoordinator from databases", ex);
        }
    }
}

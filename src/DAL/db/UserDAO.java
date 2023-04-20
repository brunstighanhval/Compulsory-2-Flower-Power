package DAL.db;

import BE.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDataAccess {

    private DatabaseConnector databaseConnector;

    public UserDAO() throws IOException {databaseConnector = DatabaseConnector.getInstance();}

    /**
     * Getting a list of all the eventkoordinators based on the selected username.
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public List<User> loadUser(String username) throws Exception {
        ArrayList<User> allEvk = new ArrayList<>();
        //SQL Query.
        String sql = "SELECT * FROM Event_Koordinator WHERE User_Name = ?";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Setting the parameters and executing the statement.
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            // Loop through rows from the database result set
            while (rs.next()) {
                //Map DB row to user object
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

    /**
     * Checking if the selected username matches a username from the database.
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public boolean validate(String username) throws Exception {
        //SQL Query.
        String sql = "SELECT * FROM Event_Koordinator WHERE User_Name = ?";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameters and executing the query.
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Failed to validate", e);
        }
        return false;
    }

    /**
     * Getting a list of all the eventkoordinator.
     * @return
     * @throws Exception
     */
    @Override
    public List<User> readEvK() throws Exception {
        ArrayList<User> Evks = new ArrayList<>();
        //SQL Query.
        String sql = "SELECT * FROM Event_Koordinator WHERE Role = 2";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Map DB row to user object
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

    /**
     * Creating a new eventkoordinator and adding it to the database.
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public User addNewEventKoordinator(String firstName, String lastName, String username, String password, int role) throws Exception {
        //SQL Query.
        String sql = "INSERT INTO Event_Koordinator (Last_Name, First_Name, User_Name, Password, Role) VALUES(?,?,?,?,?)";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query.
            stmt.setString(1, lastName);
            stmt.setString(2, firstName);
            stmt.setString(3, username);
            stmt.setString(4, password);
            stmt.setInt(5, role);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if(rs.next()){
                id = rs.getInt(1);
            }
            User eventKoordinator = new User(id, lastName, firstName, username, password, role);
            return eventKoordinator;
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not add an eventKoordinator", ex);
        }
    }

    /**
     * Delete a event koordinator from the database.
     * @param selectedKoordinator
     * @throws Exception
     */
    @Override
    public void deleteEventKoordinator(User selectedKoordinator) throws Exception {
        //SQL query.
        String sql = "DELETE FROM Event_Koordinator WHERE Id = ?";
        //Getting the connection to the database.
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameter and executing the query.
            stmt.setInt(1, selectedKoordinator.getId());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not delete this user", ex);
        }
        }
    }

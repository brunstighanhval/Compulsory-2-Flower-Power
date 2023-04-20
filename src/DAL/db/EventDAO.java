package DAL.db;

import BE.Event;
import BE.Location;
import BE.Ticket;
import BE.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventDataAccess{
    private DatabaseConnector databaseConnector;

    public EventDAO() throws IOException {databaseConnector = DatabaseConnector.getInstance();}

    /**
     * Getting a list of all the events from the database.
     * @return
     * @throws Exception
     */
    @Override
    public List<Event> getAllEvents() throws Exception {
        ArrayList<Event> allEvents = new ArrayList<>();
        //SQL query.
        String sql = "SELECT * FROM dbo.Event;";
        //Getting connection to the database.
        try (Connection conn = databaseConnector.getConnection()){
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to event object
                int eventId = rs.getInt("Event_ID");
                String name = rs.getString("Name");
                int evKId = rs.getInt("EvK_ID");
                LocalDate date =rs.getDate("Date").toLocalDate();
                LocalTime startTime=rs.getTime("Start_Time").toLocalTime();
                LocalTime endTime=rs.getTime("End_Time").toLocalTime();
                int maxTickets = rs.getInt("Max_Tickets");
                String notes = rs.getString("Notes");
                int VenueID = rs.getInt("Venue_ID");
                int verified = rs.getInt("Verified");

                //Initializing a new event.
                Event event = new Event(eventId, name, evKId, date, startTime, endTime,maxTickets,notes,VenueID,verified);
                allEvents.add(event);
            }
            return allEvents;
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }
    }

    /**
     *Getting a list of all tickets for a selected event.
     * @param event
     * @return
     * @throws Exception
     */
    @Override
    public List<Ticket> getTicketsFromEvent(Event event) throws Exception {
        ArrayList<Ticket> ticketsFromEvent = new ArrayList<>();
        //SQL Query.
        String sql = "SELECT * FROM Ticket WHERE Event_ID = ?";
        //Getting connection to the database.
        try (Connection conn = databaseConnector.getConnection()){

             PreparedStatement stmt = conn.prepareStatement(sql);
             stmt.setInt(1, event.getId());

            ResultSet rs = stmt.executeQuery();
            // Loop through rows from the database result set
            while (rs.next()) {
                int ticket_Id = rs.getInt("Ticket_ID");
                int event_Id = rs.getInt("Event_ID");
                String firstName = rs.getString("First_Name");
                String lastName =rs.getString("Last_Name");
                String mail = rs.getString("Mail");
                int type_Id = rs.getInt("Type_ID");

                //Initializing a new ticket.
                Ticket ticket = new Ticket(ticket_Id, event_Id, firstName, lastName, mail, type_Id);
                ticketsFromEvent.add(ticket);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not get Tickets from database", ex);
        }
        return ticketsFromEvent;
    }

    /**
     * Creating a new event and adding it to the database.
     * @param name
     * @param EvKId
     * @param date
     * @param start_time
     * @param end_time
     * @param max_tickets
     * @param notes
     * @param venue_id
     * @param verified
     * @return
     * @throws Exception
     */
    @Override
    public Event createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id, int verified) throws Exception {
        //SQL Query.
        String sql = "INSERT INTO Event (Name, EvK_ID, Date, Start_Time, End_Time, Max_Tickets, Notes, Venue_ID, Verified) VALUES (?,?,?,?,?,?,?,?,?)";
        //Getting database connection.
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Setting the parameters for the event.
            stmt.setString(1, name);
            stmt.setInt(2, EvKId);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setTime(4, Time.valueOf(start_time));
            stmt.setTime(5, Time.valueOf(end_time));
            stmt.setInt(6, max_tickets);
            stmt.setString(7, notes);
            stmt.setInt(8, venue_id);
            stmt.setInt(9, verified);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if(rs.next()){
                id = rs.getInt(1);
            }
            //Initializing the event.
            Event event = new Event(id, name, EvKId, date, start_time, end_time, max_tickets, notes , venue_id, verified);
            return event;
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not create a event", ex);
        }
    }

    /**
     * Deleting a selected event from the database.
     * @param event
     * @throws Exception
     */
    @Override
    public void deleteEvent(Event event) throws Exception {
        //SQL Query.
        String sql = "DELETE FROM Event WHERE Event_ID = ?";
        //Getting connection to the database.
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameter and executing the query.
            stmt.setInt(1, event.getId());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not delete this event", ex);
        }
    }

    /**
     * Updating a selected event from the database.
     * @param updatedEvent
     * @throws Exception
     */
    @Override
    public void updateEvent(Event updatedEvent) throws Exception {
        //SQL Query.
        String sql = "UPDATE Event SET Name = ?, Date = ?, Start_Time = ?, End_Time = ?, Max_Tickets = ?, Notes = ? WHERE Event_ID = ?";
        //Getting the connection to the database.
        try(Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Setting the parameters and executing it.
            stmt.setString(1, updatedEvent.getName());
            stmt.setDate(2, Date.valueOf(updatedEvent.getDate()));
            stmt.setTime(3, Time.valueOf(updatedEvent.getStart_time()));
            stmt.setTime(4, Time.valueOf(updatedEvent.getEnd_time()));
            stmt.setInt(5, updatedEvent.getMax_tickets());
            stmt.setString(6, updatedEvent.getNotes());
            stmt.setInt(7, updatedEvent.getId());

            stmt.execute();
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not edit the event", ex);
        }
    }

    @Override
    public User getEventKoordinator(int evkId) throws Exception{
        User user = null;
        //SQL Query.
        String sql = "SELECT * FROM Event_Koordinator WHERE Id = ?";
        //Getting connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, evkId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Map DB row to user object
                int id = rs.getInt("Id");
                String last_name = rs.getString("Last_Name");
                String first_name = rs.getString("First_Name");
                String user_name = rs.getString("User_Name");
                String password = rs.getString("Password");
                int role = rs.getInt("Role");

                user = new User(id, last_name, first_name, user_name, password, role);
            }
            return user;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new Exception("Could not get EventKoordinator from databases", ex);
        }
    }

    /**
     * Getting the location from the database.
     * @param LocationId
     * @return
     * @throws Exception
     */
    @Override
    public Location getLocation(int LocationId) throws Exception{
        Location location = null;
        //SQL Query.
        String sql = "SELECT * FROM Location WHERE Venue_ID = ?";
        //Getting the database connection.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, LocationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Map DB row to location.
                int id = rs.getInt("Venue_ID");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                int zip_code = rs.getInt("Zip_Code");

                location = new Location(id, name, address, zip_code);
            }
            return location;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not get EventKoordinator from databases", ex);
        }
    }

    /**
     * Update the verification status based on the selected event.
     * @param updatedEvent
     * @throws Exception
     */
    @Override
    public void updateVerficationStatus(Event updatedEvent) throws Exception {
        //SQL Query.
        String sql = "UPDATE Event SET Verified = ? WHERE Event_ID = ?";
        //Getting the database connection.
        try(Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Setting the parameters and executing the statement.
            stmt.setInt(1, updatedEvent.getVerified());
            stmt.setInt(2, updatedEvent.getId());

            stmt.execute();
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not edit the event", ex);
        }
    }
}

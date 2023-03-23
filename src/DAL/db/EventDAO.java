package DAL.db;

import BE.Event;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class EventDAO implements IEventDataAccess{

    private DatabaseConnector databaseConnector;

    public EventDAO() throws IOException {
        databaseConnector = new DatabaseConnector();
    }
@Override
    public List<Event> getAllEvents() throws Exception {
        ArrayList<Event> allEvents = new ArrayList<>();


        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Event;";


            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Song object
                int eventId = rs.getInt("Event_ID");
                String name = rs.getString("Name");
                int evKId = rs.getInt("EvK_ID");
                LocalDate date =rs.getDate("Date").toLocalDate();
                LocalTime startTime=rs.getTime("Start_Time").toLocalTime();
                LocalTime endTime=rs.getTime("End_Time").toLocalTime();
                int maxTickets = rs.getInt("Max_Tickets");
                String notes = rs.getString("Notes");
                int VenueID = rs.getInt("Venue_ID");



                Event event = new Event(eventId, name, evKId, date, startTime, endTime,maxTickets,notes,VenueID);

                allEvents.add(event);
            }

            return allEvents;

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }
    }

    @Override
    public Event createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id) throws Exception {
        String sql = "INSERT INTO Event (Name, EvK_ID, Date, Start_Time, End_Time, Max_Tickets, Notes, Venue_ID) VALUES (?,?,?,?,?,?,?,?)";
        try(Connection conn = DatabaseConnector.getInstance().getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, name);
            stmt.setInt(2, EvKId);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setTime(4, Time.valueOf(start_time));
            stmt.setTime(5, Time.valueOf(end_time));
            stmt.setInt(6, max_tickets);
            stmt.setString(7, notes);
            stmt.setInt(8, venue_id);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if(rs.next()){
                id = rs.getInt(1);
            }

            Event event = new Event(id, name, EvKId, date, start_time, end_time, max_tickets, notes , venue_id);
            return event;
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not create a event", ex);
        }
    }

    @Override
    public void deleteEvent(Event event) throws Exception {
        String sql = "DELETE FROM Event WHERE Event_ID = ?";
        try(Connection conn = DatabaseConnector.getInstance().getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, event.getId());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not delete this event", ex);
        }
    }

    @Override
    public void editEvent(Event event) throws Exception {

    }


}

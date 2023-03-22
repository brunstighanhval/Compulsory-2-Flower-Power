package DAL.db;

import BE.Event;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class EventDAO {

    private DatabaseConnector databaseConnector;

    public EventDAO() {
        this.databaseConnector = databaseConnector;
    }

    public List<Event> getAllEvents(int user) throws Exception {
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
                int evKId = rs.getInt("Ev.K_ID");
                LocalDate date =rs.getDate("Date").toLocalDate();
                LocalTime startTime=rs.getTime("Start_Time").toLocalTime();
                LocalTime endTime=rs.getTime("End_Time").toLocalTime();
                int maxTickets = rs.getInt("Max_Tickets");
                String notes = rs.getString("Notes");
                int VenueID = rs.getInt("Venue ID");



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
}

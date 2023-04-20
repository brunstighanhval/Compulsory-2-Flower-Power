package BLL;

import BE.Event;
import BE.Location;
import BE.User;
import DAL.db.EventDAO;
import DAL.db.IEventDataAccess;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventManager {
    private IEventDataAccess eventDAO;

    public EventManager() throws IOException {eventDAO = new EventDAO();}

    public List<Event> getAllEvents() throws Exception {return eventDAO.getAllEvents();}

    /**
     * Sending the create event method through the BLL.
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
    public Event createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id, int verified) throws Exception {
        return eventDAO.createEvent(name, EvKId, date, start_time, end_time, max_tickets, notes, venue_id, verified);
    }

    /**
     * Sending the delete event method through the BLL.
     * @param event
     * @throws Exception
     */
    public void deleteEvent(Event event) throws Exception{eventDAO.deleteEvent(event);}

    /**
     * Sending the update event method through the BLL.
     * @param updatedEvent
     * @throws Exception
     */
    public void updateEvent(Event updatedEvent) throws Exception {eventDAO.updateEvent(updatedEvent);}

    /**
     * Sending the get event koordinator method through the BLL.
     * @param id
     * @return
     * @throws Exception
     */
    public User getEventKoordinator(int id) throws Exception{return eventDAO.getEventKoordinator(id);}

    /**
     * Sending the get location method through the BLL.
     * @param id
     * @return
     * @throws Exception
     */
    public Location getLocation(int id) throws Exception{return eventDAO.getLocation(id);}

    /**
     * Sending the update verification status method through BLL.
     * @param updatedEvent
     * @throws Exception
     */
    public void updateVerficationStatus(Event updatedEvent) throws Exception {
        eventDAO.updateVerficationStatus(updatedEvent);
    }
}

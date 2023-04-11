package BLL;

import BE.Event;
import BE.Location;
import BE.Ticket;
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

    public List<Ticket> getTicketsFromEvent(Event event) throws Exception {return eventDAO.getTicketsFromEvent(event);}

    public Event createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id) throws Exception {
        return eventDAO.createEvent(name, EvKId, date, start_time, end_time, max_tickets, notes, venue_id);
    }

    public void deleteEvent(Event event) throws Exception{eventDAO.deleteEvent(event);}

    public void updateEvent(Event updatedEvent) throws Exception {eventDAO.updateEvent(updatedEvent);}

    public User getEventKoordinator(int id) throws Exception{return eventDAO.getEventKoordinator(id);}
    public Location getLocation(int id) throws Exception{return eventDAO.getLocation(id);}
}

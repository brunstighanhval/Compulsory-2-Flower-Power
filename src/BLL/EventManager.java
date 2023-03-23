package BLL;

import BE.Event;
import DAL.db.EventDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventManager {

    EventDAO eventDAO=new EventDAO();

    public EventManager() throws IOException {
    }


    public List<Event> getAllEvents() throws Exception {
        return eventDAO.getAllEvents();

    }
    public Event createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id) throws Exception {
        return eventDAO.createEvent(name, EvKId, date, start_time, end_time, max_tickets, notes, venue_id);
    }

    public void deleteEvent(Event event) throws Exception{
        eventDAO.deleteEvent(event);
    }

    public void updateEvent(Event updatedEvent) throws Exception {
        eventDAO.updateEvent(updatedEvent);
    }







}

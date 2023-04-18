package DAL.db;

import BE.Event;
import BE.Location;
import BE.Ticket;
import BE.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IEventDataAccess {

    List<Event> getAllEvents() throws Exception;
    Event createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id, int verified) throws Exception;
    void deleteEvent(Event event) throws Exception;
    void updateEvent(Event event) throws Exception;
    List<Ticket> getTicketsFromEvent(Event event) throws Exception;
    User getEventKoordinator(int id) throws Exception;
    Location getLocation(int locationId) throws Exception;

    void updateVerficationStatus(Event updatedEvent) throws Exception;
}

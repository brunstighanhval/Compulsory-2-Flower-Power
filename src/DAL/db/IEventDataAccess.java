package DAL.db;

import BE.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IEventDataAccess {

    List<Event> getAllEvents() throws Exception;
    Event createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id) throws Exception;
    void deleteEvent(Event event) throws Exception;
    void updateEvent(Event event) throws Exception;

}

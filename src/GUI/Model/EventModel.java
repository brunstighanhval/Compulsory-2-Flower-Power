package GUI.Model;

import BE.Event;
import BE.Ticket;
import BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventModel {
    private ObservableList<Event> eventsToBeViewed;
    private ObservableList<Ticket> ticketsToBeViewed;
    private Event createdEvent;
    private EventManager eventManager;


    public EventModel() throws Exception
    {
        eventManager = new EventManager();
        eventsToBeViewed = FXCollections.observableArrayList();
        eventsToBeViewed.addAll(eventManager.getAllEvents());
        ticketsToBeViewed = FXCollections.observableArrayList();
    }

    public ObservableList<Event> getObservableEvents() {
        return eventsToBeViewed;
    }

    public ObservableList<Ticket> getTicketsFromEvent(Event event) throws Exception
    {
        ticketsToBeViewed.clear();
        ticketsToBeViewed.addAll(eventManager.getTicketsFromEvent(event));
        return ticketsToBeViewed;
    }

    public void createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id) throws Exception {
        createdEvent = eventManager.createEvent(name, EvKId, date, start_time, end_time, max_tickets, notes, venue_id);
        eventsToBeViewed.add(createdEvent);
        showList();
    }

    public void deleteEvent(Event event) throws Exception{
        eventManager.deleteEvent(event);
        eventsToBeViewed.remove(event);
    }

    public void updateEvent(Event updatedEvent) throws Exception {
        eventManager.updateEvent(updatedEvent);
    }

    public void showList() throws Exception {
        //Update the listview
        eventsToBeViewed.clear();
        eventsToBeViewed.addAll(eventManager.getAllEvents());
    }
}

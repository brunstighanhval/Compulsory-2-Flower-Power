package GUI.Model;

import BE.Event;
import BE.Location;
import BE.Ticket;
import BE.User;
import BLL.EventManager;
import BLL.Obs;
import BLL.Update;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventModel {
    private ObservableList<Event> eventsToBeViewed;
    private ListProperty<Ticket> ticketsToBeViewed;
    private Event createdEvent;
    private EventManager eventManager;

    private Update update;

    public EventModel() throws Exception {
        eventManager = new EventManager();
        eventsToBeViewed = FXCollections.observableArrayList();
        eventsToBeViewed.addAll(eventManager.getAllEvents());
        update=new Update();
        ticketsToBeViewed=new SimpleListProperty<>();
    }

    public ObservableList<Event> getObservableEvents() {return eventsToBeViewed;}

    public ObservableList<Ticket> getTicketsFromEvent(Event event) throws Exception {
        ticketsToBeViewed.set(FXCollections.observableArrayList(update.getUpdateEvents(event)));
        return update.getUpdateEvents(event);
    }

    /**
     * Sending the data for making a new event through the GUI and adding it to the listview.
     * @param name
     * @param EvKId
     * @param date
     * @param start_time
     * @param end_time
     * @param max_tickets
     * @param notes
     * @param venue_id
     * @param verified
     * @throws Exception
     */
    public void createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id, int verified) throws Exception {
        createdEvent = eventManager.createEvent(name, EvKId, date, start_time, end_time, max_tickets, notes, venue_id, verified);
        eventsToBeViewed.add(createdEvent);
        showList();
    }

    /**
     * Sending the event for deleting through the GUI and remove it from the list.
     * @param event
     * @throws Exception
     */
    public void deleteEvent(Event event) throws Exception{
        eventManager.deleteEvent(event);
        eventsToBeViewed.remove(event);
    }

    /**
     * Sending the update event through the GUI.
     * @param updatedEvent
     * @throws Exception
     */
    public void updateEvent(Event updatedEvent) throws Exception {
        eventManager.updateEvent(updatedEvent);
    }

    /**
     * Clear the listview and re-add everything from the database.
     * @throws Exception
     */
    public void showList() throws Exception {
        //Update the listview
        eventsToBeViewed.clear();
        eventsToBeViewed.addAll(eventManager.getAllEvents());
    }

    public void startObservable(Event event) throws IOException {
        Obs obs=new Obs();
        obs.observe(event);

    }

    /**
     * Get the eventkoordinator based on an event id.
     * @param id
     * @return
     * @throws Exception
     */
    public User getEventKoordinator(int id) throws Exception{return eventManager.getEventKoordinator(id);}

    /**
     * Getting the location based on an event id.
     * @param id
     * @return
     * @throws Exception
     */
    public Location getLocation(int id) throws Exception{return eventManager.getLocation(id);}

    /**
     * Sending the verification status update through the GUI.
     * @param updatedEvent
     * @throws Exception
     */
    public void updateVerficationStatus(Event updatedEvent) throws Exception {
        eventManager.updateVerficationStatus(updatedEvent);}
}

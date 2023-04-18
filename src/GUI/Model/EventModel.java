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
import java.util.List;

public class EventModel {
    private ObservableList<Event> eventsToBeViewed;
    private ListProperty<Ticket> ticketsToBeViewed;
    private Event createdEvent;
    private EventManager eventManager;

    private Update update;

    public EventModel() throws Exception
    {
        eventManager = new EventManager();
        eventsToBeViewed = FXCollections.observableArrayList();
        eventsToBeViewed.addAll(eventManager.getAllEvents());
        update=new Update();
        ticketsToBeViewed=new SimpleListProperty<>();


    }

    public ObservableList<Event> getObservableEvents() {


        return eventsToBeViewed;
    }

    public ObservableList<Ticket> getTicketsFromEvent(Event event) throws Exception
    {
        ticketsToBeViewed.set(FXCollections.observableArrayList(update.getUpdateEvents(event)));
        return update.getUpdateEvents(event);
    }

    public void createEvent(String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id, int verified) throws Exception {
        createdEvent = eventManager.createEvent(name, EvKId, date, start_time, end_time, max_tickets, notes, venue_id, verified);
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

    public void startObservable(Event event) throws IOException {
        Obs obs=new Obs();
        obs.observe(event);

    }

    public User getEventKoordinator(int id) throws Exception{
        return eventManager.getEventKoordinator(id);
    }
    public Location getLocation(int id) throws Exception{
        return eventManager.getLocation(id);
    }
    public void updateVerficationStatus(Event updatedEvent) throws Exception {
        eventManager.updateVerficationStatus(updatedEvent);
    }

}

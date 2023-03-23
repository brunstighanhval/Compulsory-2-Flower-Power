package GUI.Model;

import BE.Event;
import BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventModel {

    private ObservableList<Event> eventsToBeViewed;
    private EventManager eventManager;
    private Event createdEvent;

    int user=1;

    public EventModel() throws Exception  {
        eventManager = new EventManager();
        eventsToBeViewed = FXCollections.observableArrayList();
        eventsToBeViewed.addAll(eventManager.getAllEvents());
    }

    public ObservableList<Event> getObservableEvents() {
        return eventsToBeViewed;
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


    public void showList() throws Exception {
        //Update the listview
        eventsToBeViewed.clear();
        eventsToBeViewed.addAll(eventManager.getAllEvents());
    }




}

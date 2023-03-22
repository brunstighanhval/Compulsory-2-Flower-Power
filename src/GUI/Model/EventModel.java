package GUI.Model;

import BE.Event;
import BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class EventModel {

    private ObservableList<Event> eventsToBeViewed;
    private EventManager eventManager;

    int user=1;

    public EventModel() throws Exception  {
        eventManager = new EventManager();
        eventsToBeViewed = FXCollections.observableArrayList();
        eventsToBeViewed.addAll(eventManager.getAllSongs(user));
    }

    public ObservableList<Event> getObservableEvents() {
        return eventsToBeViewed;
    }


    public void showList() throws Exception {
        //Update the listview
        eventsToBeViewed.clear();
        eventsToBeViewed.addAll(eventManager.getAllSongs(user));
    }




}

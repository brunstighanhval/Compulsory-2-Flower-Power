package BLL;

import BE.Event;
import DAL.db.EventDAO;

import java.io.IOException;
import java.util.List;

public class EventManager {

    EventDAO eventDAO=new EventDAO();

    public EventManager() throws IOException {
    }


    public List<Event> getAllSongs(int user) throws Exception {
        return eventDAO.getAllEvents(user);

    }







}

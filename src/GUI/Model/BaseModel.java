package GUI.Model;

import java.time.LocalDate;

public class BaseModel {

    private EventModel eventModel;
    private LocationModel locationModel;
    private TicketModel ticketModel;
    private UserModel userModel;

    public BaseModel() throws Exception{
        eventModel = new EventModel();
        locationModel = new LocationModel();
        ticketModel = new TicketModel();
        userModel = new UserModel();
    }

    public EventModel getEventModel(){return eventModel;}
    public LocationModel getLocationModel(){return locationModel;}
    public TicketModel getTicketModel(){return ticketModel;}
    public UserModel getUserModel(){return userModel;}
}

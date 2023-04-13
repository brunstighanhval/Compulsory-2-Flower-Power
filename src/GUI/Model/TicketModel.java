package GUI.Model;

import BE.Event;
import BE.Ticket;
import BLL.TicketManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketModel {
    private ObservableList<Ticket> ticketsToBeViewed;
    private TicketManager ticketManager;
    private Ticket createdTicket;
    private Event selectedEvent;
    int user=1;

    public TicketModel() throws Exception  {
        ticketManager = new TicketManager();
        ticketsToBeViewed = FXCollections.observableArrayList();
        //ticketsToBeViewed.addAll(ticketManager.getTickets(selectedEvent));
    }

    public ObservableList<Ticket> getObservableTickets() {
        return ticketsToBeViewed;
    }

    public void createTicket(int event_Id, String firstName, String lastName, String mail, int type_Id) throws Exception {
        createdTicket = ticketManager.createTicket(event_Id, firstName, lastName, mail, type_Id);
        ticketsToBeViewed.add(createdTicket);
        //showList(selectedEvent);
    }

    public void createExtraTicket(int event_Id, String firstName, String lastName, String mail, int type_Id) throws Exception {
        createdTicket = ticketManager.createTicket(event_Id, firstName, lastName, mail, type_Id);
        ticketsToBeViewed.add(createdTicket);
        //showList(selectedEvent);
    }



    public void deleteTicket(Ticket ticket) throws Exception{
        ticketManager.deleteTicket(ticket);
        ticketsToBeViewed.remove(ticket);
    }


    public void showList(Event selectedEvent) throws Exception {
        //Update the listview
        ticketsToBeViewed.clear();
        //ticketsToBeViewed.addAll(ticketManager.getTickets(selectedEvent));
    }
}
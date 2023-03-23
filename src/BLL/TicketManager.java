package BLL;

import BE.Ticket;
import DAL.db.TicketDAO;
import java.io.IOException;

public class TicketManager {
    TicketDAO ticketDAO = new TicketDAO();

    public TicketManager() throws IOException {}

    public Ticket createTicket(int event_Id, String firstName, String lastName, String mail, int type_Id) throws Exception {
        return ticketDAO.createTicket(event_Id, firstName, lastName, mail, type_Id);
    }

    public void deleteTicket(Ticket ticket) throws Exception{ticketDAO.deleteTicket(ticket);}
}
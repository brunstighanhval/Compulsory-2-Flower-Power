package DAL.db;

import BE.Location;
import BE.Ticket;

import java.util.List;

public interface ITicket {

    Ticket createTicket(int event_id, String firstName, String LastName, String mail, int type_id) throws Exception;
    Ticket createExtraTicket(int event_id, String firstName, String LastName, String mail, int type_id) throws Exception;

    void deleteTicket(Ticket ticket) throws Exception;
}

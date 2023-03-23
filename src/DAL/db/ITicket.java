package DAL.db;

import BE.Ticket;
public interface ITicket {

    Ticket createTicket(int event_id, String firstName, String LastName, String mail, int type_id) throws Exception;

    void deleteTicket(Ticket ticket) throws Exception;
}

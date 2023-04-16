package DAL.db;

import BE.Location;
import BE.Ticket;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements ITicket{

    private DatabaseConnector databaseConnector;

    public TicketDAO() throws IOException {databaseConnector = DatabaseConnector.getInstance();}

    @Override
    public Ticket createTicket(int event_Id, String firstName, String lastName, String mail, int type_Id) throws Exception {
        String sql = "INSERT INTO Ticket (Event_ID, First_Name, Last_Name, Mail, Type_ID) VALUES (?,?,?,?,?)";
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, event_Id);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, mail);
            stmt.setInt(5, type_Id);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if(rs.next()){
                id = rs.getInt(1);
            }

            Ticket ticket = new Ticket(id, event_Id, firstName, lastName, mail, type_Id);
            return ticket;
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not create a event", ex);
        }
    }


    @Override
    public Ticket createExtraTicket(int event_Id, String firstName, String lastName, String mail, int type_Id) throws Exception {
        String sql = "INSERT INTO Ticket (Event_ID, First_Name, Last_Name, Mail, Type_ID) VALUES (?,?,?,?,?)";
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, event_Id);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, mail);
            stmt.setInt(5, type_Id);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if(rs.next()){
                id = rs.getInt(1);
            }

            Ticket ticket = new Ticket(id, event_Id, firstName, lastName, mail, type_Id);
            return ticket;
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not create a event", ex);
        }
    }


    @Override
    public void deleteTicket(Ticket ticket) throws Exception {
        String sql = "DELETE FROM Ticket WHERE Ticket_ID = ?";
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ticket.getId());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not delete this Ticket", ex);
        }
    }
}

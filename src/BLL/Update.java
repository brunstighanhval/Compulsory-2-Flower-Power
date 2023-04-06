package BLL;

import BE.Event;
import BE.Ticket;
import DAL.db.EventDAO;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.IOException;

public class Update {

    EventDAO dao;

    private static ListProperty<Ticket> ticketsToBeViewed;


    public Update() throws IOException {

        dao=new EventDAO();
        ticketsToBeViewed=new SimpleListProperty<>();

    }


    public void update(Event event) {

        Platform.runLater(() -> {

            try {
                ticketsToBeViewed.setValue(FXCollections.observableArrayList(dao.getTicketsFromEvent(event)));
                System.out.println("her");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

    }

    public ListProperty<Ticket> getUpdateEvents(Event event) {
        try {
            ticketsToBeViewed.setValue(FXCollections.observableArrayList(dao.getTicketsFromEvent(event)));


        } catch (Exception e) {
            throw new RuntimeException(e);


        }
        return ticketsToBeViewed;

    }
}
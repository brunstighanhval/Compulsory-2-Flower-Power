package GUI.Controller;

import BE.EntranceTicketPDF;
import BE.Event;
import BE.Ticket;
import GUI.Model.EventModel;
import GUI.Model.TicketModel;
import GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalTime;

public class EventController extends BaseController{
    public ImageView imgLogo;

    @FXML
    private DatePicker datePicker;


    @FXML
    private ListView <Ticket> lstEventTickets;

    @FXML
    private ListView<Event> lstAllEvents;
    @FXML
    private Button btnCreateNewEvent, btnEditEvent, btnDeleteSelectedEvent, btnNewTicket, btnDeleteTicket;
    @FXML
    private TextField txtfEventName, txtfDate,txtfLocation, txtfNotes, txtfEVK, txtfStartTime, txtfEndTime ,txtfTicketsLeft;
    @FXML
    private String errorText;
    private EventModel eventModel;
    private TicketModel ticketModel;
    private UserModel userModel;
    private Event selectedEvent;

    private Ticket selectedTicket;


    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
        eventModel = getModel().getEventModel();
        ticketModel = getModel().getTicketModel();
        lstAllEvents.setItems(eventModel.getObservableEvents());
        listenerLstAllEvents();
        listenerMouseClickTickets();
        adminView();
    }

    public void listenerLstAllEvents() {
        lstAllEvents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {

            selectedEvent  = lstAllEvents.getSelectionModel().getSelectedItem();

            try {
                lstEventTickets.setItems(eventModel.getTicketsFromEvent(selectedEvent));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            labelsToShow();
        });
    }


    public void listenerMouseClickTickets()
    {
        lstEventTickets.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) { //Ved dobbeltklik kan man starte musikken
                try {
                    makePdf();
                    showPdf();
                } catch (Exception e) {
                    displayError(e);
                }
            }
        });
    }



    private void labelsToShow()
    {
        txtfEventName.setText(selectedEvent.getName());
        //txtfDate.setText(selectedEvent.getDate().toString());
        datePicker.setValue(selectedEvent.getDate());
        //lblLocation.setText();
        txtfNotes.setText(selectedEvent.getNotes());
        //txtfEVK.setText(selectedEvent.getId()+"");
        txtfStartTime.setText(selectedEvent.getStart_time().toString());
        txtfEndTime.setText(selectedEvent.getEnd_time().toString());
        txtfTicketsLeft.setText(String.valueOf(selectedEvent.getMax_tickets()));
    }

    @FXML
    private void handleEditEvent(ActionEvent actionEvent)
    {
       try{
            Event updatedEvent = lstAllEvents.getSelectionModel().getSelectedItem();
            updatedEvent.setName(txtfEventName.getText());
            updatedEvent.setDate(datePicker.getValue());
            updatedEvent.setStart_time(LocalTime.parse(txtfStartTime.getText()));
            updatedEvent.setEnd_time(LocalTime.parse(txtfEndTime.getText()));
            updatedEvent.setMax_tickets(Integer.parseInt(txtfTicketsLeft.getText()));
            updatedEvent.setNotes(txtfNotes.getText());
            eventModel.updateEvent(updatedEvent);
        } catch (Exception e){
           displayError(e);
           e.printStackTrace();
       }
    }

    @FXML
    public void handleDeleteSelectedEvent(ActionEvent actionEvent)
    {
        selectedEvent = lstAllEvents.getSelectionModel().getSelectedItem();
        if(selectedEvent!= null){
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure you wanna delete " + selectedEvent.getName());
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    eventModel.deleteEvent(selectedEvent);

                }
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCreateNewEvent(ActionEvent actionEvent) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateEventView.fxml"));
        Parent root = loader.load();

        CreateEventController controller = loader.load();
        controller.setModel(super.getModel());
        controller.setup();

        Stage stage = new Stage();
        stage.setTitle("Create new event");
        stage.setScene(new Scene(root));
        root.getStylesheets().add(getClass().getResource("/CSS/CreateEvent.css").toExternalForm());
        stage.showAndWait();
    }

    public void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(errorText);
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    @FXML
    public void handleSignOut(ActionEvent actionEvent) {

        System.exit(0);

    }

    public void createTicket(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateTicketView.fxml"));
        Parent root = loader.load();

        CreateTicketController controller = loader.load();
        controller.setModel(super.getModel());
        controller.setup();

        Stage stage = new Stage();
        stage.setTitle("Create new Ticket");
        stage.setScene(new Scene(root));
        //root.getStylesheets().add(getClass().getResource("/CSS/CreateEvent.css").toExternalForm());
        stage.showAndWait();
    }

    public void deleteTicket(ActionEvent actionEvent) throws Exception {

        selectedTicket = lstEventTickets.getSelectionModel().getSelectedItem();
        ticketModel.deleteTicket(selectedTicket);
        lstEventTickets.refresh();
    }


    private void makePdf()
    {
        EntranceTicketPDF entranceTicketPDF=new EntranceTicketPDF();
        try {
            entranceTicketPDF.makePdf(selectedEvent.getName(),selectedEvent.getDate().toString(),selectedEvent.getStart_time().toString(),
            selectedEvent.getEnd_time().toString(),selectedEvent.getNotes(),123);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            displayError(e);
        }


    }
    private void showPdf()
    {
        EntranceTicketPDF entranceTicketPDF=new EntranceTicketPDF();
        try {
            entranceTicketPDF.showPDF();
        } catch (IOException e) {
            displayError(e);
        }
    }

    private void adminView(){
        if(userModel.getLoggedinUser().getRole() == 1) {
            btnCreateNewEvent.setDisable(true);
            btnCreateNewEvent.setOpacity(0);
            btnEditEvent.setDisable(true);
            btnEditEvent.setOpacity(0);
            btnNewTicket.setDisable(true);
            btnNewTicket.setOpacity(0);
            btnDeleteTicket.setDisable(true);
            btnDeleteTicket.setOpacity(0);
        }
    }

    }




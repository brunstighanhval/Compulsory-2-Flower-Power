package GUI.Controller;

import BE.EntranceTicketPDF;
import BE.Event;
import BE.Location;
import BE.Ticket;
import GUI.Model.EventModel;
import GUI.Model.LocationModel;
import GUI.Model.TicketModel;
import GUI.Model.UserModel;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class MainController extends BaseController{
    public ListView lstEventKoordinator;
    @FXML
    private Text txtBookedTicketAndEvK;
    @FXML
    private AnchorPane acpBackground;
    @FXML
    private VBox createLocationBar, createTicketBar, createEventBar;
    @FXML
    private ListView <Ticket> lstEventTickets;
    @FXML
    private ListView<Event> lstAllEvents;
    @FXML
    private ComboBox<Location> locationsBox;
    @FXML
    private ComboBox<Event> eventBox;
    @FXML
    private TextField eventName, startTime, endTime, ticketAmount, locationName, adress, zipCode, firstNametxt, lastNametxt, mailtxt, txtfEventName, txtfStartTime, txtfEndTime ,txtfTicketsLeft, txtfEvK, txtfLocation;
    @FXML
    private TextArea notesArea, txtfNotes;
    @FXML
    private DatePicker datePick, datePicker;
    @FXML
    private Button btnEditEvent, btnNewTicket, btnDeleteTicket, btnCreateTicket, btnNewLocation, btnTEST, btnSignOut, btnDeleteSelectedEvent, createEvent, createLocation;
    @FXML
    private RadioButton standard, vip;
    private String errorText;
    private EventModel eventModel;
    private TicketModel ticketModel;
    private UserModel userModel;
    private LocationModel locationModel;
    private Event selectedEvent;
    private Ticket selectedTicket;
    private int ticketType;
    private boolean isMenuOpen;

    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
        eventModel = getModel().getEventModel();
        ticketModel = getModel().getTicketModel();
        locationModel = getModel().getLocationModel();
        lstAllEvents.setItems(eventModel.getObservableEvents());
        eventBox.setItems(eventModel.getObservableEvents());
        locationsBox.setItems(locationModel.getObservableLocations());
       // lstEventTickets.setItems(eventModel.getTicketsFromEvent(lstAllEvents.getItems().get(0)));
        listenerLstAllEvents();
        listenerMouseClickTickets();
        adminView();
    }

    public void listenerLstAllEvents() {
        lstAllEvents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {

            selectedEvent  = lstAllEvents.getSelectionModel().getSelectedItem();

            try {
                eventModel.startObservable(selectedEvent);
                lstEventTickets.setItems(eventModel.getTicketsFromEvent(selectedEvent));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                labelsToShow();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void listenerMouseClickTickets()
    {
        lstEventTickets.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) { //Ved dobbeltklik kan man starte musikken
                try {
                    selectedTicket = lstEventTickets.getSelectionModel().getSelectedItem();
                    makePdf();
                    showPdf();
                } catch (Exception e) {
                    displayError(e);
                }
            }
        });
    }

    private void labelsToShow() throws Exception {
        txtfEventName.setText(selectedEvent.getName());
        //txtfDate.setText(selectedEvent.getDate().toString());
        datePicker.setValue(selectedEvent.getDate());
        txtfLocation.setText(eventModel.getLocation(selectedEvent.getVenue_id()).toString());
        txtfNotes.setText(selectedEvent.getNotes());
        txtfEvK.setText(eventModel.getEventKoordinator(selectedEvent.getEvKId()).toString());
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

    public void createTicket() throws IOException
    {
        TranslateTransition transition = new TranslateTransition();
        createTicketBar.toFront();
        transition.setNode(createTicketBar);
        transition.setDuration(Duration.millis(150));

        if(!isMenuOpen) {
            isMenuOpen = true;
            transition.setToX(600);
            acpBackground.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        createTicket();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    acpBackground.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                }
            };
            acpBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, menuHandler);
        } else {
            isMenuOpen = false;
            transition.setToX(900);
            acpBackground.setOpacity(1);
        }
        transition.play();
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
            selectedEvent.getEnd_time().toString(),selectedEvent.getNotes(),makeTicketNumber());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            displayError(e);
        }
    }


        private int makeTicketNumber()
        {
            int eventNumbet=selectedEvent.getVenue_id()%100;
            int ticketNumber=selectedTicket.getId()%1_000_000;

              int startNumber=eventNumbet*1_000_000 +ticketNumber;


            return startNumber;
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
            btnEditEvent.setDisable(true);
            btnEditEvent.setOpacity(0);
            btnNewTicket.setDisable(true);
            btnNewTicket.setOpacity(0);
            btnDeleteTicket.setDisable(true);
            btnDeleteTicket.setOpacity(0);
            btnNewLocation.setDisable(true);
            btnNewLocation.setOpacity(0);
            btnTEST.setDisable(true);
            btnTEST.setOpacity(0);
            lstEventTickets.setDisable(true);
            lstEventTickets.setOpacity(0);
            lstEventKoordinator.setItems(userModel.getObservableEventsKoordinator());
            txtBookedTicketAndEvK.setText("Event Koordinator");
        } else{
            lstEventKoordinator.setDisable(true);
            lstEventKoordinator.setOpacity(0);
        }
    }

    public void animationTest() {

        TranslateTransition transition = new TranslateTransition();
        createEventBar.toFront();
        transition.setNode(createEventBar);
        transition.setDuration(Duration.millis(150));

        if(!isMenuOpen) {
            isMenuOpen = true;
            transition.setToX(0);
            acpBackground.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    animationTest();
                    acpBackground.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                }
            };
            acpBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, menuHandler);
        } else {
            isMenuOpen = false;
            transition.setToX(-300);
            acpBackground.setOpacity(1);
        }
        transition.play();



    }

    public void createEventVersionTwo(ActionEvent actionEvent) {
        String name = eventName.getText();
        int EvKId = userModel.getLoggedinUser().getId();
        LocalDate date = datePick.getValue();
        LocalTime start_time = LocalTime.parse(startTime.getText());
        LocalTime end_time = LocalTime.parse(endTime.getText());
        int max_tickets = Integer.parseInt(ticketAmount.getText());
        String notes = notesArea.getText();
        int venue_id = locationsBox.getSelectionModel().getSelectedItem().getId();

        try {
            eventModel.createEvent(name, EvKId, date, start_time, end_time, max_tickets, notes, venue_id);
            animationTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newLocation() {

        TranslateTransition transition = new TranslateTransition();
        createLocationBar.toFront();
        transition.setNode(createLocationBar);
        transition.setDuration(Duration.millis(150));

        if(!isMenuOpen) {
            isMenuOpen = true;
            transition.setToX(0);
            acpBackground.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    newLocation();
                    acpBackground.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                }
            };
            acpBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, menuHandler);
        } else {
            isMenuOpen = false;
            transition.setToX(-300);
            acpBackground.setOpacity(1);
        }
        transition.play();
    }

    public void createNewLocation(ActionEvent actionEvent) throws Exception {
        String name = locationName.getText();
        String address = adress.getText();
        int zip_Code = Integer.parseInt(zipCode.getText());
        locationModel.createLocation(name,address,zip_Code);
    }

    public void publishTicket(ActionEvent actionEvent) throws Exception {
        int event_ID = eventBox.getSelectionModel().getSelectedItem().getId();
        String firstName = firstNametxt.getText();
        String lastName = lastNametxt.getText();
        String mail = mailtxt.getText();
        int type = ticketType;
        ticketModel.createTicket(event_ID,firstName,lastName,mail,type);
    }

    public void vipAction(ActionEvent actionEvent)
    {
        if (vip.isSelected())
        {
            ticketType = 2;
            standard.setDisable(true);
        }
        else
        {
            standard.setDisable(false);
        }
    }

    public void standardAction (ActionEvent actionEvent)
    {
        if(standard.isSelected())
        {
            ticketType = 1;
            vip.setDisable(true);
        }
        else
        {
            vip.setDisable(false);
        }
    }
}



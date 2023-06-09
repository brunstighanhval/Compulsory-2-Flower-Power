package GUI.Controller;

import BE.*;
import DAL.db.BCrypt;
import GUI.Model.EventModel;
import GUI.Model.LocationModel;
import GUI.Model.TicketModel;
import GUI.Model.UserModel;
import jakarta.mail.MessagingException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class MainController extends BaseController{
    @FXML
    private ListView<User> lstEventKoordinator;

    @FXML
    private Text txtBookedTicketAndEvK;
    @FXML
    private AnchorPane acpBackground;
    @FXML
    private VBox createLocationBar, createTicketBar, createEventBar, createEventKoordinatorBar;
    @FXML
    private ListView <Ticket> lstEventTickets;
    @FXML
    private ListView<Event> lstAllEvents;
    @FXML
    private ComboBox<Location> locationsBox;
    @FXML
    private ComboBox<Event> eventBox;
    @FXML
    private TextField eventName, startTime, endTime, ticketAmount, locationName, adress, zipCode, firstNametxt, lastNametxt, mailtxt, txtfEventName, txtfStartTime, txtfEndTime ,txtfTicketsLeft, txtfEvK, txtfLocation, txtfFirstName, txtfLastName, txtfUsername, txtfPassword;
    @FXML
    private TextArea notesArea, txtfNotes;
    @FXML
    private DatePicker datePick, datePicker;
    @FXML
    private Button btnEditEvent, btnNewTicket, btnDeleteTicket, btnCreateTicket,
            btnNewLocation, btnTEST, btnSignOut, btnDeleteSelectedEvent, createEvent,
            createLocation, btnAddEventKoordinator, btnDeleteEventKoordinator, btnNewEventKoordinator,emailTicket, btnVerify;

    @FXML
    private RadioButton standard, vip, radioExtra;
    private String errorText;
    private EventModel eventModel;
    private TicketModel ticketModel;
    private UserModel userModel;
    private LocationModel locationModel;
    private Event selectedEvent;
    private Ticket selectedTicket;
    private int ticketType;
    private boolean isMenuOpen;
    private User selectedKoordinator;

    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
        eventModel = getModel().getEventModel();
        ticketModel = getModel().getTicketModel();
        locationModel = getModel().getLocationModel();
        lstAllEvents.setItems(eventModel.getObservableEvents());
        eventBox.setItems(eventModel.getObservableEvents());
        locationsBox.setItems(locationModel.getObservableLocations());
        lstEventTickets.setItems(eventModel.getTicketsFromEvent(lstAllEvents.getItems().get(0)));
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

            if (event.getClickCount() == 2) {
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

    /**
     * Sets all the content of the textfields when an event is selected.
     * @throws Exception
     */
    private void labelsToShow() throws Exception {
        txtfEventName.setText(selectedEvent.getName());
        datePicker.setValue(selectedEvent.getDate());
        txtfLocation.setText(eventModel.getLocation(selectedEvent.getVenue_id()).toString());
        txtfNotes.setText(selectedEvent.getNotes());
        txtfEvK.setText(eventModel.getEventKoordinator(selectedEvent.getEvKId()).toString());
        txtfStartTime.setText(selectedEvent.getStart_time().toString());
        txtfEndTime.setText(selectedEvent.getEnd_time().toString());
        txtfTicketsLeft.setText(String.valueOf(selectedEvent.getMax_tickets()));
    }

    /**
     * Getting the values of the textfields and using them to update the selected event.
     * @param actionEvent
     */
    @FXML
    private void handleEditEvent(ActionEvent actionEvent) {
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

    /**
     * Deleting a selected event if the user agrees to it.
     * @param actionEvent
     */
    @FXML
    public void handleDeleteSelectedEvent(ActionEvent actionEvent) {
        selectedEvent = lstAllEvents.getSelectionModel().getSelectedItem();
        //If the user has selected an event an alert window shows which makes sure the user wants to delete the event.
        if(selectedEvent!= null){
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure you wanna delete " + selectedEvent.getName());
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    eventModel.deleteEvent(selectedEvent);
                }
            }catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows an alert if the program fails.
     * @param t
     */
    public void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(errorText);
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    @FXML
    public void handleSignOut(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to Sign Out");
        alert.setContentText("Return back to the Login page");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/LoginView.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setModel(super.getModel());
            loginController.setup();

            stage.setTitle("Event Tickets EASV Bar");
            stage.setScene(new Scene(root));
            root.getStylesheets().add(getClass().getResource("/CSS/Login.css").toExternalForm());
            stage.show();
            closeWindow(btnSignOut);
        } else {
            // ... user chose CANCEL or closed the dialog
        }

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
            selectedEvent.getEnd_time().toString(),selectedEvent.getNotes(),makeTicketNumber(selectedEvent.getVenue_id(),selectedTicket.getId()),getTicketType());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            displayError(e);
        }
    }


        public int makeTicketNumber(int eventnumber, int ticketNumber)
        {
            int eventNumb=eventnumber%100;
            int ticketNumb=ticketNumber%1_000_000;

              int startNumber=eventNumb*1_000_000 +ticketNumb;


            return startNumber;
        }


        public int getTicketType()
        {
            return selectedTicket.getType_id();
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

    /**
     * Make the difference between event koordinator view and admin view by clearing and disabling buttons.
     */
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
            emailTicket.setDisable(true);
            emailTicket.setOpacity(0);
        } else{
            lstEventKoordinator.setDisable(true);
            lstEventKoordinator.setOpacity(0);
            btnNewEventKoordinator.setDisable(true);
            btnNewEventKoordinator.setOpacity(0);
            btnDeleteEventKoordinator.setDisable(true);
            btnDeleteEventKoordinator.setOpacity(0);
            btnVerify.setDisable(true);
            btnVerify.setOpacity(0);
        }
    }

    /**
     * Opens the create new event box.
     */
    public void openCreateNewEvent() {
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
                    openCreateNewEvent();
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

    /**
     * Gets all the information needed to create a new event and calling the method from the model.
     * @param actionEvent
     */
    public void createEventVersionTwo(ActionEvent actionEvent) {
        //Creating varibles and giving them values.
        String name = eventName.getText();
        int EvKId = userModel.getLoggedinUser().getId();
        LocalDate date = datePick.getValue();
        LocalTime start_time = LocalTime.parse(startTime.getText());
        LocalTime end_time = LocalTime.parse(endTime.getText());
        int max_tickets = Integer.parseInt(ticketAmount.getText());
        String notes = notesArea.getText();
        int venue_id = locationsBox.getSelectionModel().getSelectedItem().getId();
        int verified = 0;

        //Sending the values to the model.
        try {
            eventModel.createEvent(name, EvKId, date, start_time, end_time, max_tickets, notes, venue_id, verified);
            openCreateNewEvent();
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

         int selectedEventMaxTickets = eventBox.getSelectionModel().getSelectedItem().getMax_tickets();
         List<Ticket> selectedEventTicketsCreated = eventModel.getTicketsFromEvent(eventBox.getSelectionModel().getSelectedItem());

        if (selectedEventMaxTickets >= selectedEventTicketsCreated.size()+1) {
            ticketModel.createTicket(event_ID, firstName, lastName, mail, type);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Event is SOLD OUT!");
            alert.showAndWait();
        }

        if (radioExtra.isSelected() && selectedEventMaxTickets >= selectedEventTicketsCreated.size()+1 ) {
            ticketModel.createExtraTicket(event_ID, firstName, lastName, mail, 3);
        }
        firstNametxt.clear();
        lastNametxt.clear();
        mailtxt.clear();
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

    public void extraTicket(ActionEvent actionEvent) {

    }


    /**
     * Opening the JavaFX VBox for when a new eventkoordinator needs to be created.
     */
    public void handleCreateEventKoordinator() {
        TranslateTransition transition = new TranslateTransition();
        createEventKoordinatorBar.toFront();
        transition.setNode(createEventKoordinatorBar);
        transition.setDuration(Duration.millis(150));

        if(!isMenuOpen) {
            isMenuOpen = true;
            transition.setToX(0);
            acpBackground.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    handleCreateEventKoordinator();
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

    /**
     * Deleting an eventkoordinator after making sure the admin wants to.
     * @param actionEvent
     */
    public void handleDeleteEventKoordinator(ActionEvent actionEvent) {
        selectedKoordinator = lstEventKoordinator.getSelectionModel().getSelectedItem();
        //If the user has selected an eventkoordinator an alert window shows which makes sure the user wants to delete the eventkoordinator.
        if (selectedKoordinator != null) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure you wanna delete " + selectedKoordinator.getFirst_name() + " " + selectedKoordinator.getLast_name());
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    userModel.deleteEventKoordinator(selectedKoordinator);
                }
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets all the information needed to create a new eventkoordinator and calling the method from the model.
     * @param actionEvent
     */
    public void handleAddEventKoordinator(ActionEvent actionEvent) {
        try {
            //Binding all the data to the variables.
            String firstName = txtfFirstName.getText();
            String lastName = txtfLastName.getText();
            String username = txtfUsername.getText();
            String password = txtfPassword.getText();
            String salt = BCrypt.gensalt(12);
            password = BCrypt.hashpw(password, salt);
            int role = 2;

            //Sending the variables to the model.
            userModel.addNewEventKoordinator(firstName, lastName, username, password, role);
            handleCreateEventKoordinator();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void emailTicketAction(ActionEvent actionEvent) throws MessagingException, IOException {

        Mail mail = new Mail();

        //    if (selectedTicket!=null)
        //    mail.sendMail(selectedTicket.getMail()); //Udkommenteret da programmet ikke kan sende mail pga. it systemet.
    }

    /**
     * Asks and runs the method call for verifying an event.
     * @param actionEvent
     * @throws Exception
     */
    public void handleVerify(ActionEvent actionEvent) throws Exception {
        Event updatedEvent = lstAllEvents.getSelectionModel().getSelectedItem();
        //If the user has selected an event an alert will show. If the admin clicks okay, the event will be verified.
        if(updatedEvent != null){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you wanna verify " + selectedEvent.getName());
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            updatedEvent.setVerified(1);
            eventModel.updateVerficationStatus(updatedEvent);
            }
        }
    }
}




package GUI.Controller;

import BE.Event;
import GUI.Model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    public ImageView imgLogo;
    @FXML
    private ListView<Event> lstAllEvents;
    @FXML
    private Button btnCreateNewEvent, btnEditEvent, btnDeleteSelectedEvent;
    @FXML
    private Label lblEventuate, lblDate,lblLocation, lblNotes, lblEventCoordinator, lblStartTime, lblTicketsLeft;
    @FXML
    private String errorText;
    @FXML
    private EventModel eventModel;

    Event selectedEvent;


    public EventController() {
        try {
            eventModel=new EventModel();
        } catch (Exception e) {
            displayError(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstAllEvents.setItems(eventModel.getObservableEvents());
        listenerLstAllEvents();

    }


    public void listenerLstAllEvents() {
        lstAllEvents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {

            selectedEvent  = lstAllEvents.getSelectionModel().getSelectedItem();
            labelsToShow();

        });

    }

    private void labelsToShow()
    {
        //lblEventuate.setText();
        lblDate.setText(selectedEvent.getDate().toString());
        //lblLocation.setText();
        lblNotes.setText(selectedEvent.getNotes());
        lblEventCoordinator.setText(selectedEvent.getId()+"");
        lblStartTime.setText(selectedEvent.getStart_time().toString());
        lblTicketsLeft.setText(selectedEvent.getMax_tickets()+"");
    }



    @FXML
    private void handleEditEvent(ActionEvent actionEvent) {
    }

    @FXML
    public void handleDeleteSelectedEvent(ActionEvent actionEvent) {
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
    public void handleCreateNewEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateEventView.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Create new event");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(errorText);
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
    @FXML
    public void handleSignOut(ActionEvent actionEvent) {
    }
/*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image logo;
        try {
            logo =  new Image(new FileInputStream(("/Pictures/logo.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        imgLogo.setImage(logo);


    }

 */
}

package GUI.Controller;

import GUI.Model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    public ImageView imgLogo;
    @FXML
    private ListView lstAllEvents;
    @FXML
    private Button btnCreateNewEvent, btnEditEvent, btnDeleteSelectedEvent;
    @FXML
    private Label lblEventuate, lblDate,lblLocation, lblNotes, lblEventCoordinator, lblStartTime, lblTicketsLeft;

    private EventModel eventModel;


    public EventController() {
        try {
            eventModel=new EventModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstAllEvents.setItems(eventModel.getObservableEvents());



    }

    @FXML
    private void handleEditEvent(ActionEvent actionEvent) {
    }

    @FXML
    public void handleDeleteSelectedEvent(ActionEvent actionEvent) {
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

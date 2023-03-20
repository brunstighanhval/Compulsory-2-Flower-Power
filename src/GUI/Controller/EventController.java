package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class EventController {
    @FXML
    private ListView lstAllEvents;
    @FXML
    private Button btnCreateNewEvent, btnEditEvent, btnDeleteSelectedEvent;
    @FXML
    private Label lblEventuate, lblDate,lblLocation, lblNotes, lblEventCoordinator, lblStartTime, lblTicketsLeft;

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
}

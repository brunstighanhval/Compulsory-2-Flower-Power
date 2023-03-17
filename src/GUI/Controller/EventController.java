package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class EventController {
    @FXML
    private ListView lstAllEvents;
    @FXML
    private Button btnCreateNewEvent, btnEditEvent, btnDeleteSelectedEvent;
    @FXML
    private Label lblEventuate, lblDate,lblLocation, lblNotes, lblEventCoordinator, lblStartTime, lblTicketsLeft;

    public void handleEditEvent(ActionEvent actionEvent) {
    }

    public void handleDeleteSelectedEvent(ActionEvent actionEvent) {
    }

    public void handleCreateNewEvent(ActionEvent actionEvent) {
    }
}

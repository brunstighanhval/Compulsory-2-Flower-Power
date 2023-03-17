package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CreateEventController {
    @FXML
    private TextField txtfEventName, txtfDate, txtfStartTime, txtfNotes;
    @FXML
    private Button btnCreateNewLocation;
    @FXML
    private ComboBox cbbSelectedLocation;

    public void handleCreateNewLocation(ActionEvent actionEvent) {
    }

    public void handleSelectedLocation(ActionEvent actionEvent) {
    }
}

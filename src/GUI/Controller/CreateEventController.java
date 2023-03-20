package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateEventController {
    @FXML
    private TextField txtfEventName, txtfDate, txtfStartTime, txtfNotes;
    @FXML
    private Button btnCreateNewLocation;
    @FXML
    private ComboBox cbbSelectedLocation;

    public void handleCreateNewLocation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateNewLocationView.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Create new event");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void handleSelectedLocation(ActionEvent actionEvent) {
    }
}

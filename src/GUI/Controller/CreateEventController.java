package GUI.Controller;

import BE.Location;
import GUI.Model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable {
    @FXML
    private ComboBox<Location> cbbSelectedLocation;
    @FXML
    private TextField txtfEventName, txtfDate, txtfStartTime, txtfNotes;
    @FXML
    private Button btnCreateNewLocation;


    EventModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            model = new EventModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        cbbSelectedLocation.setItems(model.getObservableLocations());

    }

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

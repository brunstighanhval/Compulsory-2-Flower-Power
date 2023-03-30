package GUI.Controller;

import BE.Location;
import GUI.Model.EventModel;
import GUI.Model.LocationModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreateEventController extends BaseController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Location> cbbSelectedLocation;
    @FXML
    private TextField txtfEventName, txtfDate, txtfStartTime, txtfEndTime, txtfTicketAmount, txtfNotes;
    @FXML
    private Button btnCreateNewLocation, btnSaveNewEvent;
    private LocationModel locationModel;
    @FXML
    private EventModel eventModel;

    @Override
    public void setup() {
        eventModel = getModel().getEventModel();
        locationModel = getModel().getLocationModel();

        cbbSelectedLocation.setItems(locationModel.getObservableLocations());
    }

    public void handleCreateNewLocation(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateNewLocationView.fxml"));
        Parent root = loader.load();

        CreateNewLocationController controller = loader.load();
        controller.setModel(super.getModel());
        controller.setup();

        Stage stage = new Stage();
        stage.setTitle("Create new event");
        stage.setScene(new Scene(root));
        root.getStylesheets().add(getClass().getResource("/CSS/CreateNewLocation.css").toExternalForm());
        stage.showAndWait();
    }

    public void handleCreateEvent(ActionEvent actionEvent) {
        String name = txtfEventName.getText();
        int EvKId = 1;
        LocalDate date = datePicker.getValue();


        LocalTime start_time = LocalTime.parse(txtfStartTime.getText());
        LocalTime end_time = LocalTime.parse(txtfEndTime.getText());
        int max_tickets = Integer.parseInt(txtfTicketAmount.getText());
        String notes = txtfNotes.getText();
        int venue_id = cbbSelectedLocation.getSelectionModel().getSelectedItem().getId();

        try {
            eventModel.createEvent(name, EvKId, date, start_time, end_time, max_tickets, notes, venue_id);
            Stage stage = (Stage) btnSaveNewEvent.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

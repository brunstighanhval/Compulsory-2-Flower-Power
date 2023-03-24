package GUI.Controller;

import GUI.Model.EventModel;
import GUI.Model.LocationModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class BaseController {

    LocationModel model;


    public BaseController() {
        try {
            this.model = new LocationModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void closeWindow(Button btn)
    {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public LocationModel getModel() {
        return model;
    }
    //   public abstract void setup(); //This is here so we can override it in SongViewController



    public abstract void setupModel();
    //Runs as soon as the Window opens

}

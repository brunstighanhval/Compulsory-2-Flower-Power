package GUI.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class BaseController {

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
}

package GUI.Controller;

import GUI.Model.BaseModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class BaseController {

    private BaseModel baseModel;

    /**
     * Getters and setters.
     * @param baseModel
     */
    public void setModel(BaseModel baseModel){this.baseModel = baseModel;}
    public BaseModel getModel(){return baseModel;}

    /**
     * Closing the window on the chosen button.
     * @param btn
     */
    public void closeWindow(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    /**
     * A global error message.
     * @param t
     */
    public void displayError(Throwable t){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public abstract void setup() throws Exception;
}

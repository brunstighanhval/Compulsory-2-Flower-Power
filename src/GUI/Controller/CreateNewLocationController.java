package GUI.Controller;

import GUI.Model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateNewLocationController {

   private EventModel model;

   @FXML
    private TextField txtfNameOfLocation, txtfAddress, txtZip_Code;
    @FXML
    private Button btnAdd;

    public void handleAdd(ActionEvent actionEvent) throws Exception {
     model = new EventModel();

     String name = txtfNameOfLocation.getText();
     String address = txtfAddress.getText();
     int zip_Code = Integer.parseInt(txtZip_Code.getText());


        model.createLocation(name, address,zip_Code);

    }
}
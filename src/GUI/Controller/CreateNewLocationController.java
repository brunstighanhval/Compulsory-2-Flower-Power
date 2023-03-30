package GUI.Controller;

import GUI.Model.LocationModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewLocationController extends BaseController implements Initializable {
    @FXML
    private LocationModel model;
    @FXML
    private TextField txtfNameOfLocation, txtfAddress, txtZip_Code;
    @FXML
    private Button btnAdd;
    @Override
    public void setupModel() {

    }
    public void handleAdd(ActionEvent actionEvent) throws Exception {
     model = new LocationModel();

     String name = txtfNameOfLocation.getText();
     String address = txtfAddress.getText();
     int zip_Code = Integer.parseInt(txtZip_Code.getText());

        model.createLocation(name, address,zip_Code);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupModel();
    }
}

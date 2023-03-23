package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtfUsername;
    @FXML
    private Button btnSignIn;
    @FXML
    private PasswordField paswPassword;
    @FXML
    private AnchorPane acpBackground;

    public void handleSignIn(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/EventView.fxml"));
        Parent root = loader.load();
        stage.setTitle("Event Tickets EASV Bar");
        stage.setScene(new Scene(root));
        root.getStylesheets().add(getClass().getResource("/CSS/Event.css").toExternalForm());
        stage.show();
        Stage stage1 = (Stage) btnSignIn.getScene().getWindow();
        stage1.close();
    }
}

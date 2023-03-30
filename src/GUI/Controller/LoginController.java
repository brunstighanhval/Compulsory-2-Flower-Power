package GUI.Controller;

import BE.User;
import DAL.db.UserDAO;
import GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController extends BaseController {
    @FXML
    private TextField txtfUsername;
    @FXML
    private Button btnSignIn;
    @FXML
    private PasswordField paswPassword;
    @FXML
    private AnchorPane acpBackground;
    private User user;
    private UserModel userModel;

    public void handleSignIn(ActionEvent actionEvent) throws Exception {
        UserDAO userDAO = new UserDAO();
        String email = txtfUsername.getText();
        String password = paswPassword.getText();
        boolean flag = userDAO.validate(email, password);
        if(!flag) {
            loginFailedAlert();
        } else {
            user = userModel.loadUser(email, password).get(0);
            userModel.setLoggedinUser(user);
            openMainWindow();
        }
    }

    private void openMainWindow() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/EventView.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();

        stage.setTitle("Event Tickets EASV Bar");
        stage.setScene(new Scene(root));
        //root.getStylesheets().add(getClass().getResource("/CSS/Event.css").toExternalForm());
        stage.show();
        closeWindow(btnSignIn);
    }
    private void loginFailedAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Wrong username or password");
        alert.showAndWait();
    }

    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
    }
}

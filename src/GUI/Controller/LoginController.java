package GUI.Controller;

import DAL.db.BCrypt;
import BE.User;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class  LoginController extends BaseController {
    @FXML
    private ImageView logoImgView;
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

    /**
     * Choosing what to do when the user tries to log in. If the username and password are correct, it should open the main window.
     * @param actionEvent
     * @throws Exception
     */
    public void handleSignIn(ActionEvent actionEvent) throws Exception {
        String email = txtfUsername.getText();
        String password = paswPassword.getText();
        //Check if the username matches a name from the database, else show error.
        boolean flag = userModel.validate(email);
        if(!flag) {
            loginFailedAlert();
        } else {
            //Set the user based on the correct username and get the matching password.
            user = userModel.loadUser(email).get(0);
            //Check if the database password matches the user password, else show error.
            if(BCrypt.checkpw(password, user.getPassword())) {
                userModel.setLoggedinUser(user);
                openMainWindow();
            }
            else{
                loginFailedAlert();
            }
        }
    }

    /**
     * Calls for a new stage which shows the main view of the application.
     * @throws Exception
     */
    private void openMainWindow() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/EventView.fxml"));
        Parent root = loader.load();
        //Setting the controller and calling the getmodel method.
        MainController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();

        stage.setTitle("Event Tickets EASV Bar");
        stage.setScene(new Scene(root));
        root.getStylesheets().add(getClass().getResource("/CSS/Event.css").toExternalForm());
        stage.show();
        stage.setResizable(false);
        closeWindow(btnSignIn);
    }

    /**
     * Shows and error alert if the user does something wrong.
     */
    private void loginFailedAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Wrong username or password");
        alert.showAndWait();
    }

    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
        logoImgView.setImage(new Image("Pictures/logo.png"));
    }
}

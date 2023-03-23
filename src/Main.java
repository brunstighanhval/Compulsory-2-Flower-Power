import DAL.db.LocationDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        LocationDAO eventDAO = new LocationDAO();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GUI/View/LoginView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        //primaryStage.getScene().getStylesheets().add(getClass().getResource("CSS/Event.css").toExternalForm());



        primaryStage.setTitle("Login page");
       // primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}

package GUI.Controller;

import BE.Event;
import GUI.Model.EventModel;
import GUI.Model.TicketModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CreateTicketController extends BaseController {
    @FXML
    private ComboBox<Event> cbEventList;
    @FXML
    private TextField txtFirstName, txtLastName, txtMail;
    @FXML
    private RadioButton rbVIP, rbStandard;
    @FXML
    private Button btnFinish;

    private EventModel eventModel;
    private TicketModel ticketModel;
    int ticketType;

    //Runs as soon as the Window opens
    @Override
    public void setupModel()
    {
        try {
            eventModel = new EventModel();
            ticketModel = new TicketModel();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cbEventList.setItems(eventModel.getObservableEvents());
    }

    //When user hits 'FINISH' Button
    public void createTicket(ActionEvent actionEvent) throws Exception {
        int event_ID = cbEventList.getSelectionModel().getSelectedItem().getId();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String mail = txtMail.getText();
        int type = ticketType;

        ticketModel.createTicket(event_ID,firstName,lastName,mail,type);

        closeWindow(btnFinish);
    }

    //When user toggles 'VIP' Radio Button
    public void vipAction(ActionEvent actionEvent)
    {
        if (rbVIP.isSelected())
        {
            ticketType = 2;
            rbStandard.setDisable(true);
        }
        else
        {
            rbStandard.setDisable(false);
        }
    }

    //When user toggles 'STANDARD' Radio Button
    public void standardAction (ActionEvent actionEvent)
    {
        if(rbStandard.isSelected())
        {
            ticketType = 1;
            rbVIP.setDisable(true);
        }
        else
        {
            rbVIP.setDisable(false);
        }
    }
}


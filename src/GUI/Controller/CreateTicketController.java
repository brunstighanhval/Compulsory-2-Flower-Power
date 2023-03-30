package GUI.Controller;

import BE.Event;
import GUI.Model.EventModel;
import GUI.Model.TicketModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class CreateTicketController extends BaseController implements Initializable {
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
    public void setupModel() {
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

        ticketModel.createTicket(event_ID, firstName, lastName, mail, type);
        final String user="mlkaer2@hotmail.com";//change accordingly
        final String password="illidan1ocanada1";//change accordingly

        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
       // "mail.javatpoint.com" localhost
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(mail));
            message.setSubject("Message Aleart");

            //3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("Her er billeten");

            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            String filename = "BE/Barcode.png";//change accordingly
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);


            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            //6) set the multiplart object to the message object
            message.setContent(multipart );

            //7) send message
            Transport.send(message);

            System.out.println("message sent....");
        }catch (MessagingException ex) {ex.printStackTrace();}



















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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupModel();
    }

}


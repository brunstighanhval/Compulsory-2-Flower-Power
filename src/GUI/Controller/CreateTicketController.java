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
import javax.mail.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.*;
import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class CreateTicketController extends BaseController implements Initializable {
    private static final int SMTPNumber = 587;
    private static final String SMTPName = "outlook.office365.com";
    private static final String user="mlkaer2@hotmail.com";
    private static final String password="illidan1ocanada1";
    private final String from = "mlkaer1@hotmail.com";
    private final String to = "mlkaer2@hotmail.com";
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



        // Assuming you are sending email from localhost
        String host = "smtp.office365.com";
        String host1 = "mlkaer2@hotmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });


        try {
            // Create a new MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set the sender and recipient of the message
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // change this later

            // Set the subject of the message
            message.setSubject("Message Alert");

            // Create the first MimeBodyPart object and set the text message
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("Her er billeten");

            // Create the second MimeBodyPart object and attach the PDF file to it
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            String filename = "GUI/Controller/Test.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);

            // Create a MimeMultipart object and add the MimeBodyPart objects to it
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            // Set the content of the email message to the MimeMultipart object
            message.setContent(multipart);

            // Send the email message
            Transport.send(message);

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

        // Close the window
        closeWindow(btnFinish);
    }
    public void sendEmail() {
        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }

        });
    }

        public Properties getEmailProperties () {
            final Properties config = new Properties();
            config.put("mail.smtp.auth", "true");
            config.put("mail.smtp.starttls.enable", "true");
            config.put("mail.smtp.host", SMTPName);
            config.put("mail.smtp.port", SMTPNumber);
            return config;
        }


        //When user toggles 'VIP' Radio Button
        public void vipAction (ActionEvent actionEvent)
        {
            if (rbVIP.isSelected()) {
                ticketType = 2;
                rbStandard.setDisable(true);
            } else {
                rbStandard.setDisable(false);
            }
        }

        //When user toggles 'STANDARD' Radio Button
        public void standardAction (ActionEvent actionEvent)
        {
            if (rbStandard.isSelected()) {
                ticketType = 1;
                rbVIP.setDisable(true);
            } else {
                rbVIP.setDisable(false);
            }
        }

        @Override
        public void initialize (URL location, ResourceBundle resources){
            setupModel();
        }
    }


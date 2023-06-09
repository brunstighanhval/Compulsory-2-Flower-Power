package BE;



import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Mail {

    Properties prop=new Properties();
    String userName, password;
    Session session;
    MimeBodyPart mimeBodyPart;

    Message message;

    public void sendMail(String mail) throws IOException, MessagingException {


        emailSettings();
        emailMessage(mail);
        emailAttachment();

        Transport.send(message);

    }
    /**
     * Making the attachment to a mail.
     * @throws Exception
     */

    private void emailAttachment() throws MessagingException, IOException { //Her tilføjes attachment

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        MimeBodyPart attachmentBodyPart = new MimeBodyPart();



        String path = "billet.pdf";
        boolean filesExits = Files.exists(Path.of(path)); //check if the file exists

        if (filesExits)
        {
            File file = new File(path);
            attachmentBodyPart.attachFile(file);
            multipart.addBodyPart(attachmentBodyPart);
            message.setContent(multipart);
        }
    }

    /**
     * Givning the Message in the mail.
     * @param mail
     * @throws Exception
     */


    private void emailMessage(String mail) throws MessagingException {

        message = new MimeMessage(session);

        message.setFrom(new InternetAddress(userName));


        String recipient=mail;

        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject("Mail Subject");


        String msg = "This is your ticket";


        mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

    }
    //The emailSettings() method loads the email settings from a
    // configuration file and sets them in the prop object


    /**
     * Setting up the email settings
     * @throws Exception
     */


    private void emailSettings() throws IOException {
        //prop is a Properties object that contains email settings.
        final String PROP_FILE = "Config/email.settings";


        prop.put("mail.smtp.auth",true);
        prop.put("mail.smtp.starttles.enable","true");
        prop.put("mail.smtp.host","smtp.simply.com");
        prop.put("mail.smtp.port","25");
        prop.put("mail.smtp.ssl.trust", "smtp.simply.com");



        Properties emailProperties = new Properties();
        emailProperties.load(new FileInputStream(new File(PROP_FILE)));

         userName=emailProperties.getProperty("userName");
         password=emailProperties.getProperty("password");


         session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

    }


}

package Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailTLS {

    public void sentMail(InternetAddress email, String subject, String content) {

        final String username = "udaralatest@gmail.com";
        final String password = "xcuzqhcfazpdoscg";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("udaralatest@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("" + email) // comma seperated lots of gmails
            );
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Email Sent. Done.");

        } catch (MessagingException e) {
            System.out.println("Error...! Email not send.");
            e.printStackTrace();
        }
    }

}
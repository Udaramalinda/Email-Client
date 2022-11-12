package Email;

import javax.mail.internet.InternetAddress;

public class EmailSend {

    private SendEmailTLS sendEmailTLS;

    EmailSend(){
        // create the object of SendEmailTLS
        sendEmailTLS = new SendEmailTLS();

    }

    public void newEmailSend( Email email ){

        // get the details that want to send the email
        // by the getter methods
        InternetAddress emailAddress = email.getEmailAddress();
        String subject = email.getSubject();
        String content = email.getContent();

        // then send the email
        sendEmailTLS.sentMail(emailAddress, subject, content);


    }
}

package Email;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.time.LocalDate;

public class Email implements Serializable {

    private InternetAddress emailAddress;
    private String subject;
    private String content;
    private LocalDate sentDate;

    public Email( String emailAddress, String subject, String content ) throws AddressException {
        // get the inputs that want to make a email object
        // coverts the email string to the Internet address object type
        this.emailAddress = new InternetAddress(emailAddress);
        this.subject = subject;
        this.content = content;
        // create the attribute that the time that email object create
        this.sentDate = LocalDate.now();
    }

    public InternetAddress getEmailAddress(){
        return this.emailAddress;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getContent(){
        return this.content;
    }

    public LocalDate getSentDate(){
        return this.sentDate;
    }


}
